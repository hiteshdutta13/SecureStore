package com.secure.store.controller;

import com.secure.store.constant.PageConstants;
import com.secure.store.modal.UserDTO;
import com.secure.store.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class Register {
    @Autowired
    UserService userService;

    @PostMapping
    public String registerUser(@ModelAttribute UserDTO userDto, Model model) {
        model.addAttribute(PageConstants.ATTRIBUTE_USER, new UserDTO());
        userService.register(userDto);
        return "redirect:/login";
    }

    @GetMapping
    public String register(HttpServletRequest req, HttpServletResponse res, Model model) {
        model.addAttribute(PageConstants.ATTRIBUTE_USER, new UserDTO());
        return PageConstants.PAGE_SIGN_UP;
    }
}
