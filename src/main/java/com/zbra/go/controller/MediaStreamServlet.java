package com.zbra.go.controller;

import com.zbra.go.log.DefaultLogFactory;
import com.zbra.go.log.Log;
import com.zbra.go.model.ImageFile;
import com.zbra.go.service.ImageService;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.nio.file.Paths;
import java.util.Optional;

public class MediaStreamServlet extends HttpServlet {

    private static final Log log = DefaultLogFactory.createSimpleLog(MediaStreamServlet.class);

    private final ImageService imageService;

    public MediaStreamServlet(ImageService imageService) {
        this.imageService = imageService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processStream(req, resp);
    }

    private void processStream(HttpServletRequest request, HttpServletResponse response) throws IOException {
        doProcessStream(request, response, true);
    }

    private void doProcessStream(HttpServletRequest request, HttpServletResponse response, boolean enableContent) throws IOException {
        // Get requested file by path info.
        final String requestedFile = request.getPathInfo();

        log.info("Requested file: [%s]", requestedFile);

        // Check if file is actually supplied to the request URL.
        if (requestedFile == null) {
            // Do your thing if the file is not supplied to the request URL.
            // Throw an exception, or send 404, or show default/warning page, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // URL-decode the file name (might contain spaces and on) and prepare file object.
        final String path = URLDecoder.decode(requestedFile, "UTF-8");
        final String fileName = FilenameUtils.getName(path);
        final int extensionIndex = fileName.indexOf(".");
        final String baseName = extensionIndex > -1 ? fileName.substring(0, extensionIndex) : fileName;
        String mediaId = baseName;

        if (baseName.endsWith("_encoded")) {
            mediaId = mediaId.substring(0, baseName.lastIndexOf("_"));
        }

        final Optional<ImageFile> mediaFileMaybe = imageService.findByMediaId(mediaId);
        if (!mediaFileMaybe.isPresent()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        final ImageFile mediaFile = mediaFileMaybe.get();

        log.info("Downloading media [%s]...", path);

        final String filePath = Paths.get(mediaFile.getPath()).toString();
        final File file = new File(filePath);
        // Check if file actually exists in filesystem.
        if (!file.exists()) {
            // Do your thing if the file appears to be non-existing.
            // Throw an exception, or send 404, or show default/warning page, or just ignore it.
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        String contentType = getServletContext().getMimeType(file.getName());
        if (contentType == null) {
            contentType = "image/png";
        }

        response.setContentType(contentType);
        response.setHeader("Content-Length", String.valueOf(file.length()));

        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(file);
            response.setContentLength(in.available());
            out = response.getOutputStream();
            log.info("Copying file [%s] to output stream...", fileName);
            IOUtils.copy(in, out);
            log.info("File [%s] copy to output stream completed", fileName);
        } finally {
            if (log.isDebugEnabled()) {
                log.debug("Closing input stream from file [%s]...", fileName);
            }
            IOUtils.closeQuietly(in);
            if (log.isDebugEnabled()) {
                log.debug("Closing output stream from response at [%s]...", fileName);
            }
            IOUtils.closeQuietly(out);
            if (log.isDebugEnabled()) {
                log.debug("Everything was closed from [%s]", fileName);
            }
        }
    }
}
