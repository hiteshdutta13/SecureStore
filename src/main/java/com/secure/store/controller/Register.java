package com.secure.store.controller;

import com.secure.store.constant.PageConstants;
import com.secure.store.modal.UserDTO;
import com.secure.store.service.UserService;
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
        var response = userService.register(userDto);
        if(response.isSuccess()) {
            return "redirect:/login";
        }else {
            model.addAttribute(PageConstants.ATTRIBUTE_MESSAGE, response.getMessage());
            return register(model);
        }
    }

    @GetMapping
    public String register(Model model) {
        model.addAttribute(PageConstants.ATTRIBUTE_USER, new UserDTO());
        return PageConstants.PAGE_SIGN_UP;
    }
}
