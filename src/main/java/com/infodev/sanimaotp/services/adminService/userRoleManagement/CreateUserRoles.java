package com.infodev.sanimaotp.services.adminService.userRoleManagement;

import com.infodev.sanimaotp.entities.Role;
import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.pojo.UserManagement.AddRolePojo;
import com.infodev.sanimaotp.pojo.UserManagement.AddUserPojo;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CreateUserRoles {
    GlobalResponse createRole(AddRolePojo addRolePojo);
    GlobalResponse createUser (AddUserPojo addUserPojo, Authentication authentication);
    List<Role> getRoleList();
    List<Role> getActiveRoles();
}
