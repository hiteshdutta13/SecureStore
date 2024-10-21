package com.secure.store.controller;

import com.secure.store.constant.PageConstants;
import com.secure.store.service.PlanService;
import com.secure.store.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {
    @Autowired
    UserService userService;

    @Autowired
    PlanService planService;

    @GetMapping("/")
    public String home(Model model, @RequestParam(value = "folderId", required = false, defaultValue = "-1") Long folderId) {
        return this.drive(model, folderId);
    }
    @GetMapping("/drive")
    public String drive(Model model, @RequestParam(value = "folderId", required = false, defaultValue = "-1") Long folderId) {
        model.addAttribute(PageConstants.ATTRIBUTE_USER, userService.getActive());
        model.addAttribute(PageConstants.ATTRIBUTE_PAGE, "default");
        model.addAttribute(PageConstants.ATTRIBUTE_SELECTED_FOLDER_Id, (folderId != null && folderId > 0 ? folderId: -1));
        return PageConstants.PAGE_INDEX;
    }

    @GetMapping("/drive/settings")
    public String settings(Model model) {
        model.addAttribute(PageConstants.ATTRIBUTE_USER, userService.getActive());
        model.addAttribute(PageConstants.ATTRIBUTE_PAGE, "settings");
        return PageConstants.PAGE_INDEX;
    }
    @GetMapping("/drive/bin")
    public String bin(Model model) {
        model.addAttribute(PageConstants.ATTRIBUTE_USER, userService.getActive());
        model.addAttribute(PageConstants.ATTRIBUTE_PAGE, "bin");
        return PageConstants.PAGE_INDEX;
    }

    @GetMapping("/drive/upgrade")
    public String upgrade(Model model) {
        model.addAttribute(PageConstants.ATTRIBUTE_USER, userService.getActive());
        model.addAttribute(PageConstants.ATTRIBUTE_PAGE, "upgrade");
        model.addAttribute("plans", planService.getAll());
        return PageConstants.PAGE_INDEX;
    }
}
