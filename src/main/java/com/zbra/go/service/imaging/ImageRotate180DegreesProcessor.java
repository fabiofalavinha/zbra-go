package com.zbra.go.service.imaging;

import java.awt.image.BufferedImage;

public class ImageRotate180DegreesProcessor implements ImageRotateProcessor {
    private static final double PORTRAIT_ROTATE_180_ANGLE = 180d;

    @Override
    public BufferedImage rotate(BufferedImage image) {
        return ImageConverter.rotate(image, PORTRAIT_ROTATE_180_ANGLE);
    }
}
