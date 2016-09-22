package com.zbra.go.service.imaging;

import com.zbra.go.model.Image;
import com.zbra.go.model.ImageFile;

import java.io.IOException;

public interface ImageFileProcessor {

    ImageFile process(Image image) throws IOException;

}
