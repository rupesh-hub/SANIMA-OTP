package com.infodev.sanimaotp.controller.AdminController.UserController;

import com.infodev.sanimaotp.controller.base.BaseController;
import com.infodev.sanimaotp.enums.ModuleEnum;
import com.infodev.sanimaotp.pojo.UserManagement.AddRolePojo;
import com.infodev.sanimaotp.services.adminService.user.UserService;
import com.infodev.sanimaotp.services.adminService.userRoleManagement.CreateUserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/role_")
public class RoleController extends BaseController {

    private final UserService userService;
    private final CreateUserRoles createUserRoles;

    @Autowired
    public RoleController(UserService userService, CreateUserRoles createUserRoles) {
        this.userService = userService;
        this.createUserRoles = createUserRoles;
        this.module1 = ModuleEnum.ROLE.getAbbreviation();
    }

    @GetMapping
    @PreAuthorize("hasPermission(#this.this.module1,'')")
    public String addRole(final Model model) {
        model.addAttribute("roleList", userService.getRoles());
        return "pages/userManagement/AddRole";
    }

    @GetMapping(path = "/{id}")
    public  ResponseEntity<?> getRoleById(@PathVariable final int id) {
        return new ResponseEntity(userService.getRoleById(id), HttpStatus.OK);
    }

    @PostMapping("/addRole_")
    public ResponseEntity<?> addRole(@RequestBody AddRolePojo addRolePojo) {
        return new ResponseEntity<>(createUserRoles.createRole(addRolePojo), HttpStatus.OK);
    }
}
