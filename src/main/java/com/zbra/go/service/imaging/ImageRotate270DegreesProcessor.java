package com.zbra.go.service.imaging;

import java.awt.image.BufferedImage;

public class ImageRotate270DegreesProcessor implements ImageRotateProcessor {
    private static final double PORTRAIT_ROTATE_270_ANGLE = 270d;

    @Override
    public BufferedImage rotate(BufferedImage image) {
        return ImageConverter.rotate(image, PORTRAIT_ROTATE_270_ANGLE);
    }
}
