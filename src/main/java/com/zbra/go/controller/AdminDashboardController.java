package com.zbra.go.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/jedai")
public class AdminDashboardController {

    @RequestMapping(value = {"/", ""}, method = RequestMethod.GET)
    public ModelAndView dashboard() {
        return new ModelAndView("private/dashboard");
    }
}
