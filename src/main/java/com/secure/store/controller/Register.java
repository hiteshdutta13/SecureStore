package com.secure.store.controller;

import com.secure.store.modal.UserDTO;
import com.secure.store.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/register")
public class Register {
    protected final Log logger = LogFactory.getLog(getClass());
    @Autowired
    private UserService userService;

    @PostMapping
    public String registerUser(@ModelAttribute UserDTO userDto, ModelAndView model) {
        userService.register(userDto);
        return "redirect:/login";
    }

    @GetMapping
    public String register(HttpServletRequest req, HttpServletResponse res, ModelAndView model) {
        model.addObject("user", new UserDTO());
        return "signup";
    }
}
