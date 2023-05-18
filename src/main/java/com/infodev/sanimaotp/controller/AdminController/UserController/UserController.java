package com.infodev.sanimaotp.controller.AdminController.UserController;

import com.infodev.sanimaotp.controller.base.BaseController;
import com.infodev.sanimaotp.entities.Role;
import com.infodev.sanimaotp.enums.ModuleEnum;
import com.infodev.sanimaotp.pojo.ChangePasswordPojo;
import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.pojo.UserManagement.AddUserPojo;
import com.infodev.sanimaotp.pojo.UserManagement.PasswordUpdatePojo;
import com.infodev.sanimaotp.services.adminService.branch.IBranchService;
import com.infodev.sanimaotp.services.adminService.user.UserService;
import com.infodev.sanimaotp.services.adminService.userRoleManagement.CreateUserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user_")
public class UserController extends BaseController {

    private final CreateUserRoles createUserRoles;
    private final UserService userService;
    private final IBranchService branchService;

    @Autowired
    public UserController(CreateUserRoles createUserRoles, UserService userService, IBranchService branchService) {
        this.createUserRoles = createUserRoles;
        this.userService = userService;
        this.branchService = branchService;
        this.module1 = ModuleEnum.USER.getAbbreviation();
    }


    @GetMapping
    @PreAuthorize("hasPermission(#this.this.module1,'')")
    public String userForm(Model model) {
        model.addAttribute("userList", userService.getAllUser());
        model.addAttribute("branchList",branchService.allActiveBranches());

        try {
            List lis = new ArrayList<>();
            for (Role r : createUserRoles.getActiveRoles()) {
                Map tmap = new LinkedHashMap();
                tmap.put("role", r.getName());
                lis.add(tmap);
            }
            model.addAttribute("roleList", lis);
        } catch (Exception e) {
            model.addAttribute("roleList", "[]");
        }
        return "pages/userManagement/AddUsers";
    }


    @PostMapping("/addUser_")
    public ResponseEntity<GlobalResponse> addUser(@RequestBody AddUserPojo addUserPojo, Authentication authentication) {
        return new ResponseEntity<>(createUserRoles.createUser(addUserPojo, authentication), HttpStatus.OK);
    }

    @GetMapping("/changeStatus_/{username}")
    public String updateStatus(@PathVariable final String username, final Authentication authorization) {
        userService.changeStatus(username, authorization);
        return "redirect:/user_";
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getByUsername(@PathVariable final String username){
        return new ResponseEntity(userService.getByUsername(username), HttpStatus.OK);
    }

    @PostMapping("/updatePassword_")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody PasswordUpdatePojo passwordUpdatePojo,
                                            final Authentication authentication){
        return new ResponseEntity(userService.updatePassword(passwordUpdatePojo, authentication), HttpStatus.OK);
    }

    @PostMapping("/changePassword_")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordPojo changePassPojo,
                                            final Authentication authentication) {
        return new ResponseEntity(userService.changePassword(changePassPojo, authentication), HttpStatus.OK);
    }

}
