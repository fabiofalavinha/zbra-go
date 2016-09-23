package com.zbra.go.controller.util;

import com.zbra.go.model.ImageFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;

@Component
public class ImageUrlFactory {

    private static final String URL_TEMPLATE = "%s://%s:%d/image/%s";
    private static final String IMAGE_PATH_TEMPLATE = "images/%s";

    @Value("${server.port}")
    private int defaultServerPort;

    public String buildUrl(ImageFile imageFile, HttpServletRequest request) {
        if (imageFile == null) {
            throw new IllegalArgumentException("'imageFile' argument is null");
        }
        return doBuildUrl(IMAGE_PATH_TEMPLATE, imageFile.getFilename(), request);
    }

    public String buildThumbnailUrl(ImageFile imageFile, HttpServletRequest request) {
        return doBuildUrl(IMAGE_PATH_TEMPLATE, imageFile.getThumbnailFilename(), request);
    }

    private String doBuildUrl(String imageTemplate, String fileName, HttpServletRequest request) {
        if (fileName == null || fileName.isEmpty()) {
            return null;
        }

        final URL requestURL;
        try {
            requestURL = new URL(request.getRequestURL().toString());
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid request state", e);
        }
        final String scheme = request.getScheme();
        final String host = requestURL.getHost();

        int port = requestURL.getPort();
        if (port == -1) {
            port = defaultServerPort;
        }

        final String imagePath = String.format(imageTemplate, fileName);

        return String.format(URL_TEMPLATE, scheme, host, port, imagePath);
    }

}
