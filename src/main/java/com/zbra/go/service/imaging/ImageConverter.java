package com.zbra.go.service.imaging;

import net.coobird.thumbnailator.Thumbnails;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public final class ImageConverter {

    public static Image createImage(String imageFileName, String description, Class<?> targetClassType) {
        final String imageFilePath = new File(String.format("settings/images/%s", imageFileName)).getAbsolutePath();
        return new ImageIcon(imageFilePath, description).getImage();
    }

    public static BufferedImage rotate(BufferedImage image, double angle) {
        final BufferedImage newImage = new BufferedImage(image.getHeight(), image.getWidth(), image.getType());
        final Graphics2D graphics = (Graphics2D) newImage.getGraphics();
        graphics.rotate(Math.toRadians(angle), newImage.getWidth() / 2, newImage.getHeight() / 2);
        graphics.translate((newImage.getWidth() - image.getWidth()) / 2, (newImage.getHeight() - image.getHeight()) / 2);
        graphics.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
        return newImage;
    }

    public static BufferedImage resize(BufferedImage image, int width, int height) throws IOException {
        return Thumbnails.of(image).size(width, height).asBufferedImage();
    }

    public static BufferedImage scale(BufferedImage image, double scaleFactor) throws IOException {
        return Thumbnails.of(image).scale(scaleFactor).asBufferedImage();
    }

    public static Image createImage(String imageFileName, String description) {
        final String imageFilePath = new File(String.format("settings/images/%s", imageFileName)).getAbsolutePath();
        return new ImageIcon(imageFilePath, description).getImage();
    }
}
