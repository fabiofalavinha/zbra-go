package com.zbra.go.service;

import com.zbra.go.model.Image;
import com.zbra.go.model.ImageFile;
import com.zbra.go.persistence.ImageFileRepository;
import com.zbra.go.service.imaging.ImageFileProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
class DefaultImageService implements ImageService {

    @Autowired
    private ImageFileProcessor imageFileProcessor;

    @Autowired
    private ImageFileRepository imageFileRepository;

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
}
