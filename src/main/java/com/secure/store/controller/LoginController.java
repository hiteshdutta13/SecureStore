package com.secure.store.controller;

import com.secure.store.constant.PageConstants;
import com.secure.store.modal.UserDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    @GetMapping
    public String askToLogin(@RequestParam(value = "error", required = false, defaultValue = "0") int error, Model model) {
        model.addAttribute(PageConstants.ATTRIBUTE_USER, new UserDTO());
        if(error != 0) {
            model.addAttribute(PageConstants.ATTRIBUTE_MESSAGE, "Invalid username or password");
        }
        return PageConstants.PAGE_SIGN_IN;
    }

}
