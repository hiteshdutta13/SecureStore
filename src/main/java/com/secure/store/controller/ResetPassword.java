package com.secure.store.controller;

import com.secure.store.constant.PageConstants;
import com.secure.store.modal.ResetPasswordDTO;
import com.secure.store.modal.UserDTO;
import com.secure.store.service.UserService;
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
    public String askForEmail(Model model) {
        model.addAttribute(PageConstants.ATTRIBUTE_USER, new UserDTO());
        model.addAttribute(PageConstants.ATTRIBUTE_PAGE, "email");
        model.addAttribute(PageConstants.ATTRIBUTE_MESSAGE, "");
        return PageConstants.PAGE_RESET_PASSWORD;
    }

    @GetMapping("/change")
    public String askToResetPassword(@RequestParam("token") String token, Model model) {
        model.addAttribute(PageConstants.ATTRIBUTE_USER, new UserDTO());
        model.addAttribute(PageConstants.ATTRIBUTE_MESSAGE, "");
        var response = userService.validate(token);
        if(response.isSuccess()) {
            model.addAttribute("token", token);
            model.addAttribute(PageConstants.ATTRIBUTE_PAGE, "change");
        }else {
            model.addAttribute(PageConstants.ATTRIBUTE_PAGE, "expired");
            model.addAttribute(PageConstants.ATTRIBUTE_MESSAGE, response.getMessage());
        }
        return PageConstants.PAGE_RESET_PASSWORD;
    }

    @PostMapping("/change")
    public String changePassword(@ModelAttribute ResetPasswordDTO resetPassword, Model model) {
        model.addAttribute(PageConstants.ATTRIBUTE_USER, new UserDTO());
        model.addAttribute(PageConstants.ATTRIBUTE_MESSAGE, "");
        var response = userService.changePassword(resetPassword);
        if(response.isSuccess()) {
            model.addAttribute(PageConstants.ATTRIBUTE_PAGE, "success");
            model.addAttribute(PageConstants.ATTRIBUTE_MESSAGE, response.getMessage());
        }else {
            model.addAttribute(PageConstants.ATTRIBUTE_PAGE, "change");
            model.addAttribute(PageConstants.ATTRIBUTE_MESSAGE, response.getMessage());
        }
        return PageConstants.PAGE_RESET_PASSWORD;
    }

    @PostMapping("/email")
    public String sendResetLinkToEmail(@ModelAttribute UserDTO userDto, Model model) {
        model.addAttribute(PageConstants.ATTRIBUTE_USER, new UserDTO());
        var response = userService.resetPassword(userDto.getEmail());
        model.addAttribute("requestedEmail", "");
        if(response.isSuccess()) {
            model.addAttribute(PageConstants.ATTRIBUTE_PAGE, "token");
            model.addAttribute(PageConstants.ATTRIBUTE_MESSAGE, response.getMessage());
        }else {
            model.addAttribute(PageConstants.ATTRIBUTE_PAGE, "email");
            model.addAttribute(PageConstants.ATTRIBUTE_MESSAGE, response.getMessage());
            model.addAttribute("requestedEmail", userDto.getEmail());
        }
        return PageConstants.PAGE_RESET_PASSWORD;
    }
}
