package com.zbra.go.controller.dto;

import com.zbra.go.controller.util.ImageUrlFactory;
import com.zbra.go.model.ImageFile;

public final class ImageFileConverter {

    public static ImageFileDTO toImageFile(ImageFile imageFile, ImageUrlFactory imageUrlFactory) {
        final String url = imageUrlFactory.buildUrl(imageFile);
        final String thumbnailUrl = imageUrlFactory.buildThumbnailUrl(imageFile);

        final ImageFileDTO dto = new ImageFileDTO();
        dto.setMediaId(imageFile.getMediaId());
        dto.setUrl(url);
        dto.setThumbnailUrl(thumbnailUrl);
        return null;
    }

    private ImageFileConverter() {
    }
}
