package com.zbra.go.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
public class LevelController {

    @RequestMapping(value = "/photo", method = RequestMethod.POST)
    public void uploadImage(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        // TODO
        // 1) create Image and ImageFile class
        // 2) populate ImageFile object with file input stream
        // 3) process image file using orientation metadata
        // 4) store image and thumbnail
        // 5) return ImageFile
    }

}
