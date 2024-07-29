package com.secure.store.controller;

import com.secure.store.modal.UserDTO;
import com.secure.store.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reset/password")
public class ResetPassword {
    @Autowired
    UserService userService;

    @GetMapping
    public String forgotPass(HttpServletRequest req, HttpServletResponse res, Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("page", "email");
        model.addAttribute("message", "");
        return "resetpassword";
    }

    @GetMapping("/change")
    public String resetPass(@RequestParam("token") String token, HttpServletRequest req, HttpServletResponse res, Model model) {
        model.addAttribute("user", new UserDTO());
        model.addAttribute("message", "");
        var response = userService.validate(token);
        if(response.isSuccess()) {
            model.addAttribute("page", "change");
        }else {
            model.addAttribute("page", "expired");
            model.addAttribute("message", response.getMessage());
        }
        return "resetpassword";
    }

    @PostMapping(value = "/email")
    public String forgotPassE(@ModelAttribute UserDTO userDto, Model model) {
        model.addAttribute("user", new UserDTO());
        var response = userService.resetPassword(userDto.getEmail());
        if(response.isSuccess()) {
            model.addAttribute("page", "token");
            model.addAttribute("message", response.getMessage());
        }else {
            model.addAttribute("page", "email");
            model.addAttribute("message", response.getMessage());
        }
        return "resetpassword";
    }
}
