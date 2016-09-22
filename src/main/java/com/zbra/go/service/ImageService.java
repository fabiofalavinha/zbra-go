package com.zbra.go.service;

import com.zbra.go.model.Image;
import com.zbra.go.model.ImageFile;

public interface ImageService {

    ImageFile store(Image image);

}
