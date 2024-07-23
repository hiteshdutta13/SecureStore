package com.secure.store.controller;

import com.secure.store.modal.UserDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/login")
public class LoginController {
    protected final Log logger = LogFactory.getLog(getClass());
    @GetMapping
    public String loginPage(HttpServletRequest req, HttpServletResponse res, ModelAndView model) {
        model.addObject("user", new UserDTO());
        return "signin";
    }
}
