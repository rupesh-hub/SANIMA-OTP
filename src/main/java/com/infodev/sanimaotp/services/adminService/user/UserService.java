package com.infodev.sanimaotp.services.adminService.user;

import com.infodev.sanimaotp.entities.Role;
import com.infodev.sanimaotp.pojo.ChangePasswordPojo;
import com.infodev.sanimaotp.pojo.RolePojo;
import com.infodev.sanimaotp.pojo.UserManagement.PasswordUpdatePojo;
import com.infodev.sanimaotp.pojo.UserPojo;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface UserService {

    List<UserPojo> getAllUser();
    void changeStatus(final String username, final Authentication authentication);
    List<Role> getRoles();
    RolePojo getRoleById(int id);
    UserPojo getByUsername(String username);
    Boolean updatePassword(PasswordUpdatePojo passwordUpdatePojo,Authentication authentication);
    Boolean changePassword(ChangePasswordPojo changePasswordPojo, Authentication authentication);
}
