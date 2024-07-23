package com.secure.store.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {
    protected final Log logger = LogFactory.getLog(getClass());

    @GetMapping("/")
    public String home(HttpServletRequest req, HttpServletResponse res, ModelAndView model) {
        model.addObject("currentView", "home");
        return "index";
    }

    @GetMapping("/my-drive")
    public String drive(HttpServletRequest req, HttpServletResponse res, ModelAndView model) {
        model.addObject("currentView", "drive");
        return "index";
    }
}
