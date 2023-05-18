package com.infodev.sanimaotp.controller;

import com.infodev.sanimaotp.controller.base.BaseController;
import com.infodev.sanimaotp.enums.ModuleEnum;
import com.infodev.sanimaotp.pojo.UserPojo;
import com.infodev.sanimaotp.services.adminService.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
public class DashboardController extends BaseController {

    private final UserService userService;

    @Autowired
    public DashboardController(UserService userService) {
        this.userService = userService;
        this.module1 = ModuleEnum.DASHBOARD.getAbbreviation();
    }

    @RequestMapping({"/admin", "/dashboard"})
    public String adminPage(final Principal principal, final Model model) {
        final String username = principal.getName();
        model.addAttribute("user", username);

        return "admin";
    }

    @ResponseBody
    @GetMapping("/change-pass-req")
    public ResponseEntity<Boolean> isPasswordChangeRequired(final Principal principal) {
        return ResponseEntity.ok(userService.getByUsername(principal.getName()).isRequiredPassChange());
    }

}
