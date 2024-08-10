package com.secure.store.controller;

import com.secure.store.constant.PageConstants;
import com.secure.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {
    @Autowired
    UserService userService;
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute(PageConstants.ATTRIBUTE_USER, userService.getActive());
        model.addAttribute(PageConstants.ATTRIBUTE_PAGE, "default");
        return PageConstants.PAGE_INDEX;
    }

    @GetMapping("/settings")
    public String drive(Model model) {
        model.addAttribute(PageConstants.ATTRIBUTE_USER, userService.getActive());
        model.addAttribute(PageConstants.ATTRIBUTE_PAGE, "settings");
        return PageConstants.PAGE_INDEX;
    }
}
