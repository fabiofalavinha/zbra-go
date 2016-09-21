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
    public void uploadPhoto(@RequestParam("file") MultipartFile file, HttpServletRequest request) {

    }

}
