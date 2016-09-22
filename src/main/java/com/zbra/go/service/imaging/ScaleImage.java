package com.zbra.go.service.imaging;

import java.awt.image.BufferedImage;
import java.io.IOException;

class ScaleImage {
    private static final int MIN_IMAGE_WIDTH_MOBILE = 480;
    private static final int MIN_IMAGE_WIDTH_TABLET = 768;
    private static final int MIN_IMAGE_WIDTH_DESKTOP = 992;
    private static final int MIN_IMAGE_WIDTH_HIGH_DESKTOP = 1382;

    public static ScaleImage newScaledImage(BufferedImage image) {
        double scaleFactor;
        final int width = image.getWidth();
        if (width <= MIN_IMAGE_WIDTH_MOBILE) {
            scaleFactor = 1.0d;
        } else if (width > MIN_IMAGE_WIDTH_MOBILE && width <= MIN_IMAGE_WIDTH_TABLET) {
            scaleFactor = 0.8d;
        } else if (width > MIN_IMAGE_WIDTH_TABLET && width <= MIN_IMAGE_WIDTH_DESKTOP) {
            scaleFactor = 0.6d;
        } else if (width > MIN_IMAGE_WIDTH_DESKTOP && width <= MIN_IMAGE_WIDTH_HIGH_DESKTOP) {
            scaleFactor = 0.4d;
        } else {
            scaleFactor = 0.2d;
        }
        return new ScaleImage(image, scaleFactor);
    }

    private BufferedImage image;
    private double defaultScaleFactor;

    private ScaleImage(BufferedImage image, double defaultScaleFactor) {
        this.image = image;
        this.defaultScaleFactor = defaultScaleFactor;
    }

    public BufferedImage scale(double newScaleFactor) throws IOException {
        return ImageConverter.scale(image, newScaleFactor);
    }

    public BufferedImage scale() throws IOException {
        return image = ImageConverter.scale(image, defaultScaleFactor);
    }

    public BufferedImage resize(int width, int height) throws IOException {
        return image = ImageConverter.resize(image, width, height);
    }
}
