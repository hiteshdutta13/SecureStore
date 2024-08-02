package com.secure.store.controller;

import com.secure.store.constant.PageConstants;
import com.secure.store.modal.UserDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String askToLogin( Model model) {
        model.addAttribute(PageConstants.ATTRIBUTE_USER, new UserDTO());
        return PageConstants.PAGE_SIGN_IN;
    }

}
