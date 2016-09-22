package com.zbra.go.service.imaging;

import java.awt.image.BufferedImage;

public class ImageRotate90DegreesProcessor implements ImageRotateProcessor {
    private static final double PORTRAIT_ROTATE_90_ANGLE = 90d;

    @Override
    public BufferedImage rotate(BufferedImage image) {
        return ImageConverter.rotate(image, PORTRAIT_ROTATE_90_ANGLE);
    }
}