package com.zbra.go.service.imaging;

import com.zbra.go.log.Log;
import com.zbra.go.log.LogFactory;
import com.zbra.go.model.Image;
import com.zbra.go.model.ImageFile;
import com.zbra.go.model.MediaStorageSettings;
import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.*;

@Component
public class DefaultImageFileProcessor implements ImageFileProcessor {

    @Autowired
    private LogFactory logFactory;

    @Autowired
    private ImageRotateProcessorFactory imageRotateProcessorFactory;

    @Autowired
    private MediaStorageSettings mediaStorageSettings;

    @Override
    public ImageFile process(Image image) throws IOException {
        final Log log = logFactory.createLog(DefaultImageFileProcessor.class);

        final ImageFile imageFile = toImageFile(image);

        OutputStream outputStream = null;
        try {
            Path imageAbsolutePath = Paths.get(imageFile.getPath()).toAbsolutePath();

            outputStream = Files.newOutputStream(imageAbsolutePath, CREATE, CREATE_NEW, WRITE);

            final InputStream originalInputStream = image.getInputStream();
            final byte[] imageData = IOUtils.toByteArray(originalInputStream);
            final ImageMetadata metadata = Imaging.getMetadata(imageData);
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(imageData));

            if (metadata instanceof JpegImageMetadata) {
                final JpegImageMetadata jpegImageMetadata = (JpegImageMetadata) metadata;
                final TiffField field = jpegImageMetadata.findEXIFValueWithExactMatch(TiffTagConstants.TIFF_TAG_ORIENTATION);
                if (field != null) {
                    final ImageRotateProcessor imageRotateProcessor =
                        imageRotateProcessorFactory.getImageRotateProcessor(field.getIntValue());
                    bufferedImage = imageRotateProcessor.rotate(bufferedImage);
                }
            }

            ImageIO.write(bufferedImage, imageFile.getExtension(), outputStream);

            // write image thumbnail based on device scale proportions
            final ScaleImage scaleImage = ScaleImage.newScaledImage(bufferedImage);
            if (scaleImage != null) {
                final BufferedImage scaleBufferedImage = scaleImage.scale();
                try (final OutputStream scaledImageOutputStream = Files.newOutputStream(Paths.get(imageFile.getThumbnailPath()), CREATE, CREATE_NEW, WRITE)) {
                    ImageIO.write(scaleBufferedImage, imageFile.getExtension(), scaledImageOutputStream);
                }
            }
        } catch (IOException ex) {
            log.exception(ex, "I/O exception was raised persisting image on disk [%s]", imageFile.getPath());
        } catch (ImageReadException ex) {
            log.exception(ex, "Error reading image metadata [%s]", imageFile.getPath());
            throw new IOException(ex);
        } finally {
            IOUtils.closeQuietly(outputStream);
        }

        return imageFile;
    }

    private ImageFile toImageFile(Image image) {
        final String imageAbsolutePath = mediaStorageSettings.getImageAbsolutePath(image.getFilename()).toAbsolutePath().toString();
        final String thumbnailAbsolutePath = imageAbsolutePath.concat(mediaStorageSettings.getThumbnailExtension());

        final ImageFile mediaFile = new ImageFile();
        mediaFile.setMediaId(image.getId());
        mediaFile.setPath(imageAbsolutePath);
        mediaFile.setThumbnailPath(thumbnailAbsolutePath);

        return mediaFile;
    }
}
