package com.zbra.go.service.imaging;

import org.apache.commons.imaging.formats.tiff.constants.TiffTagConstants;
import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

@Component
class DefaultImageRotateProcessorFactory implements ImageRotateProcessorFactory {

    private static final Map<Integer, ImageRotateProcessor> imageRotateProcessorMap = new HashMap<>();

    static {
        imageRotateProcessorMap.put(TiffTagConstants.ORIENTATION_VALUE_ROTATE_90_CW, new ImageRotate90DegreesProcessor());
        imageRotateProcessorMap.put(TiffTagConstants.ORIENTATION_VALUE_ROTATE_180, new ImageRotate180DegreesProcessor());
        imageRotateProcessorMap.put(TiffTagConstants.ORIENTATION_VALUE_ROTATE_270_CW, new ImageRotate270DegreesProcessor());
    }

    public ImageRotateProcessor getImageRotateProcessor(int rotateType) {
        if (imageRotateProcessorMap.containsKey(rotateType)) {
            return imageRotateProcessorMap.get(rotateType);
        }

        return new NoImageRotateProcessor();
    }

    private class NoImageRotateProcessor implements ImageRotateProcessor {

        @Override
        public BufferedImage rotate(BufferedImage image) {
            return image;
        }
    }
}
