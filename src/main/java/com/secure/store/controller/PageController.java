package com.secure.store.controller;

import com.secure.store.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    protected final Log logger = LogFactory.getLog(getClass());
    @Autowired
    UserService userService;
    @GetMapping("/")
    public String home(HttpServletRequest req, HttpServletResponse res, Model model) {
        model.addAttribute("currentView", "home");
        model.addAttribute("user", userService.getActive());
        return "index";
    }

    @GetMapping("/my-drive")
    public String drive(HttpServletRequest req, HttpServletResponse res, Model model) {
        model.addAttribute("currentView", "drive");
        model.addAttribute("user", userService.getActive());
        return "index";
    }
}
