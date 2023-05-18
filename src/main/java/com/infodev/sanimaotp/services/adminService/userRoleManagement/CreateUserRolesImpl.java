package com.infodev.sanimaotp.services.adminService.userRoleManagement;

import com.infodev.sanimaotp.dao.BranchRepository;
import com.infodev.sanimaotp.dao.RoleRepository;
import com.infodev.sanimaotp.dao.UserRepository;
import com.infodev.sanimaotp.dao.UserRoleRepository;
import com.infodev.sanimaotp.entities.Branch;
import com.infodev.sanimaotp.entities.Role;
import com.infodev.sanimaotp.entities.User;
import com.infodev.sanimaotp.entities.UserRole;
import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.pojo.UserLogPojo;
import com.infodev.sanimaotp.pojo.UserManagement.AddRolePojo;
import com.infodev.sanimaotp.pojo.UserManagement.AddUserPojo;
import com.infodev.sanimaotp.services.adminService.userlog.UserLogService;
import com.infodev.sanimaotp.services.utils.convertors.UserConverter;
import com.infodev.sanimaotp.services.utils.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.GsonFactoryBean;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CreateUserRolesImpl implements CreateUserRoles {

    public static final String ROLE_NOT_FOUND_WITH_ID = "Role not found with id '%s'";
    public static final String ROLE_CREATED_SUCCESS = "Role created success.";
    public static final String ROLE_UPDATED_SUCCESS = "Role updated success.";

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final UserLogService _uLogService;
    private final BranchRepository branchRepository;

    @Override
    public GlobalResponse createRole(final AddRolePojo addRolePojo) {
        final Integer roleId = addRolePojo.getId();
        String message = null;

        if (roleId == null || roleId == 0) {
            //TODO: save new role - logic
            Role role = new Role(addRolePojo.getRoleName(), addRolePojo.getRoleDescription());
            role.setStatus(1);
            roleRepository.save(role);
            message = ROLE_CREATED_SUCCESS;
        } else {
            //TODO: existing role update - logic
            Role role = roleRepository
                    .findById(roleId)
                    .orElseThrow(() ->
                            new RuntimeException(String.format(ROLE_NOT_FOUND_WITH_ID, roleId)));

            role.setName(addRolePojo.getRoleName());
            role.setDescription(addRolePojo.getRoleDescription());
            roleRepository.save(role);
            message = ROLE_UPDATED_SUCCESS;

        }

        return GlobalResponse
                .builder()
                .data(addRolePojo)
                .message(message)
                .status(HttpStatus.CREATED.value())
                .build();
    }

    @Override
    @Transactional
    public GlobalResponse createUser(final AddUserPojo userPojo, Authentication authentication) {

        final Integer userId = userPojo.getUserId();
        final List<UserRole> userRoles = new ArrayList<>();
        final String[] roleNames = userPojo.getRoleList();

        if (userId == null) {//new user
            final User newUser = new User(userPojo.getUserName(), SecurityUtils.passwordEncoder().encode(userPojo.getPassword()), userPojo.getStatus(), new ArrayList());
            newUser.setRequiredPassChange(true);//always set required password change to true - when new user is created

            for (String roleName : roleNames) {
                final Role role =
                        Optional.ofNullable(roleRepository.findRoleByRoleName(roleName))
                                .orElseThrow(() -> new RuntimeException(String.format("'%s' role is not defined", roleName)));
                userRoles.add(new UserRole(newUser, role));
            }

            newUser.setUserRole(userRoles);
            /*ASSIGN BRANCH*/
            newUser.setBranch(getBranch(userPojo.getBranchName()));

            final User savedUser = userRepository.save(newUser);

            try {
                _uLogService.saveUserLog(UserLogPojo
                        .builder()
                        .rDateTime(LocalDateTime.now())
                        .taskBy(userRepository.findByUsername(authentication.getName()).get().getId())
                        .taskType("new user created")
                        .extraInfo1(UserConverter.objectToJson(savedUser))
                        .extraInfo2("")
                        .extraInfo3("")
                        .build());
            } catch (Exception e) {
                log.info("Failed to save log");
            }

            return GlobalResponse
                    .builder()
                    .data(savedUser)
                    .message("User added successfully.")
                    .status(1)
                    .build();
        } else { //user already exist but role will be assigned
            //get user by user id
            final User existingUser = userRepository.findById(userId).orElse(null);

            final Role role =
                    Optional.ofNullable(roleRepository.findRoleByRoleName(roleNames[0]))
                            .orElseThrow(() -> new RuntimeException(String.format("'%s' role is not defined", roleNames[0])));

            Optional<UserRole> userRole = userRoleRepository.findByUserId(userId);

            if (userRole.isPresent())
                userRoleRepository.delete(userRole.get());

            userRoles.add(new UserRole(existingUser, role));

            existingUser.setUserRole(userRoles);

            existingUser.setBranch(getBranch(userPojo.getBranchName()));

            final User updatedUser = userRepository.save(existingUser);

            try {
                _uLogService.saveUserLog(UserLogPojo
                        .builder()
                        .rDateTime(LocalDateTime.now())
                        .taskBy(userRepository.findByUsername(authentication.getName()).get().getId())
                        .taskType("user role updated")
                        .extraInfo1(UserConverter.objectToJson(existingUser))
                        .extraInfo2(UserConverter.objectToJson(updatedUser))
                        .extraInfo3("")
                        .build());
            } catch (Exception e) {
                log.info("Failed to save log");
            }
            return GlobalResponse
                    .builder()
                    .data(updatedUser)
                    .message("User updated successfully.")
                    .status(1)
                    .build();
        }

    }

    @Override
    public List<Role> getRoleList() {
        return roleRepository.findAll();
    }

    @Override
    public List<Role> getActiveRoles() {
        return roleRepository.fetchAll();
    }

    private Branch getBranch(final String branchName) {
        return branchRepository.findByName(branchName)
                .orElseThrow(() -> new RuntimeException(String.format("no branch found with the name [%s]", branchName)));
    }
}
