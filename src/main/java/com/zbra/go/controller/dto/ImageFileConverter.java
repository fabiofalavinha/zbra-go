package com.zbra.go.controller.dto;

import com.zbra.go.controller.util.ImageUrlFactory;
import com.zbra.go.model.ImageFile;

import javax.servlet.http.HttpServletRequest;

public final class ImageFileConverter {

    public static ImageFileDTO toImageFile(ImageFile imageFile, ImageUrlFactory imageUrlFactory, HttpServletRequest request) {
        final String url = imageUrlFactory.buildUrl(imageFile, request);
        final String thumbnailUrl = imageUrlFactory.buildThumbnailUrl(imageFile, request);

        final ImageFileDTO dto = new ImageFileDTO();
        dto.setMediaId(imageFile.getMediaId());
        dto.setUrl(url);
        dto.setThumbnailUrl(thumbnailUrl);
        return dto;
    }

    private ImageFileConverter() {
    }
}
