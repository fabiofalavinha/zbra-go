package com.zbra.go.service;

import com.zbra.go.model.Image;
import com.zbra.go.model.ImageFile;
import com.zbra.go.model.StorageSettings;
import com.zbra.go.persistence.ImageFileRepository;
import com.zbra.go.service.imaging.ImageFileProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystemException;
import java.nio.file.Paths;
import java.util.Optional;

@Service
class DefaultImageService implements ImageService {

    @Autowired
    private ImageFileProcessor imageFileProcessor;

    @Autowired
    private ImageFileRepository imageFileRepository;

    @Autowired
    private StorageSettings storageSettings;

    @PostConstruct
    public void setupDirectory() throws FileSystemException, FileNotFoundException {
        File basePath = Paths.get(storageSettings.getBasePath()).toAbsolutePath().toFile();
        if (!basePath.exists()) {
            boolean created = basePath.mkdir();
            if (!created) {
                throw new FileSystemException(String.format("Can't create base path directory [%s]. Are we have permission to create directory? Check server path permissions.", basePath.toString()));
            }
        } else {
            if (!basePath.isDirectory()) {
                throw new FileNotFoundException(String.format("Base path isn't a directory [%s]. Check 'storage' settings into application properties file.", basePath.toString()));
            }
        }
    }

    @Transactional
    @Override
    public ImageFile store(Image image) {
        try {
            ImageFile imageFile = imageFileProcessor.process(image);
            imageFileRepository.save(imageFile);
            return imageFile;
        } catch (IOException e) {
            throw new IllegalStateException(String.format("Error processing image [%s]", image.getName()));
        }
    }


    @Transactional(readOnly = true)
    @Override
    public Optional<ImageFile> findByMediaId(String mediaId) {
        return Optional.ofNullable(imageFileRepository.findByMediaId(mediaId));
    }
}
