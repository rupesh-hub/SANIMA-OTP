package com.infodev.sanimaotp.controller.moduleController;

import com.infodev.sanimaotp.controller.base.BaseController;
import com.infodev.sanimaotp.enums.ModuleEnum;
import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.pojo.ModuleAssignRequest;
import com.infodev.sanimaotp.services.adminService.user.UserService;
import com.infodev.sanimaotp.services.module.ModuleService;
import com.infodev.sanimaotp.services.roleModule.RoleModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin/roleModule_")
public class RoleModuleController extends BaseController {

    private final UserService userService;
    private final ModuleService moduleService;
    private final RoleModuleService roleModuleService;
    private final RoleModuleService _rModuleService;

    @Autowired
    public RoleModuleController(UserService userService, ModuleService moduleService, RoleModuleService roleModuleService, RoleModuleService rModuleService) {
        this.userService = userService;
        this.moduleService = moduleService;
        this.roleModuleService = roleModuleService;
        this._rModuleService = rModuleService;
        this.module1 = ModuleEnum.ROLE_MODULE_SETUP.getAbbreviation();
    }

    @GetMapping(path = "/")
    @PreAuthorize("hasPermission(#this.this.module1,'')")
    public String getPage(final Model model) {
        model.addAttribute("roles", userService.getRoles());
        model.addAttribute("modules", moduleService.allActiveModules());
        model.addAttribute("roleModuleList", roleModuleService.allRoleModules());
        return "pages/moduleManagement/role-module-setup";
    }

    @PostMapping(path = "/")
    public ResponseEntity<GlobalResponse> assignModulesToRole(@RequestBody @Valid final ModuleAssignRequest moduleAssignRequest) {
        return new ResponseEntity<>(_rModuleService.assignModulesToRole(moduleAssignRequest), HttpStatus.OK);
    }

    @GetMapping("/getRoleModule/{roleId}")
    public ResponseEntity<?> getRoleModule(@PathVariable Integer roleId) {
        return new ResponseEntity<>(_rModuleService.roleModuleByRoleId(roleId), HttpStatus.OK);
    }

}
