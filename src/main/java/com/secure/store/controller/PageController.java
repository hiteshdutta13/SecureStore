package com.secure.store.controller;

import com.secure.store.service.GlobalService;
import com.secure.store.service.UserServiceIf;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PageController {
    protected final Log logger = LogFactory.getLog(getClass());
    @Autowired
    UserServiceIf userServiceIf;
    @GetMapping("/")
    public String home(HttpServletRequest req, HttpServletResponse res, Model model) {
        model.addAttribute("currentView", "home");
        model.addAttribute("user", userServiceIf.getActive());
        return "index";
    }

    @GetMapping("/my-drive")
    public String drive(HttpServletRequest req, HttpServletResponse res, Model model) {
        model.addAttribute("currentView", "drive");
        model.addAttribute("user", userServiceIf.getActive());
        return "index";
    }
}
