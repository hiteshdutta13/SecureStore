package com.secure.store.controller;

import com.secure.store.constant.PageConstants;
import com.secure.store.modal.UserDTO;
import com.secure.store.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/login")
public class LoginController {

    @GetMapping
    public String loginPage(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Model model) {
        model.addAttribute(PageConstants.ATTRIBUTE_USER, new UserDTO());
        return PageConstants.PAGE_SIGN_IN;
    }

}
