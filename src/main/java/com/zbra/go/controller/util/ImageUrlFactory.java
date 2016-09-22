package com.zbra.go.controller.util;

import com.zbra.go.model.ImageFile;

import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;

public class ImageUrlFactory {

    private static final String URL_TEMPLATE = "%s://%s:%d/image/%s";
    private static final String IMAGE_PATH_TEMPLATE = "images/%s";

    public static ImageUrlFactory newImageUrlFactory(HttpServletRequest request) {
        try {
            final URL requestURL = new URL(request.getRequestURL().toString());
            final String scheme = request.getScheme();
            final String host = requestURL.getHost();
            final int port = requestURL.getPort();

            return new ImageUrlFactory(scheme, host, port);
        } catch (MalformedURLException ex) {
            return null;
        }
    }

    private final String scheme;
    private final String host;
    private final int port;

    private ImageUrlFactory(String scheme, String host, int port) {
        this.scheme = scheme;
        this.host = host;
        this.port = port;
    }

    public String buildUrl(ImageFile imageFile) {
        if (imageFile == null) {
            throw new IllegalArgumentException("'imageFile' argument is null");
        }
        return doBuildUrl(IMAGE_PATH_TEMPLATE, imageFile.getFilename());
    }

    public String buildThumbnailUrl(ImageFile imageFile) {
        return doBuildUrl(IMAGE_PATH_TEMPLATE, imageFile.getThumbnailFilename());
    }

    private String doBuildUrl(String imageTemplate, String fileName) {
        if (fileName == null || fileName.isEmpty()) {
            return null;
        }

        final String imagePath = String.format(imageTemplate, fileName);

        return String.format(URL_TEMPLATE, scheme, host, port, imagePath);
    }

}
