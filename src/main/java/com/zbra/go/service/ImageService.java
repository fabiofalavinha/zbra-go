package com.zbra.go.service;

import com.zbra.go.model.Image;
import com.zbra.go.model.ImageFile;

import java.util.Optional;

public interface ImageService {

    ImageFile store(Image image);
    Optional<ImageFile> findByMediaId(String mediaId);
}
