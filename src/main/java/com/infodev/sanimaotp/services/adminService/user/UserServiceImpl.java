package com.infodev.sanimaotp.services.adminService.user;

import com.infodev.sanimaotp.dao.RoleRepository;
import com.infodev.sanimaotp.dao.UserRepository;
import com.infodev.sanimaotp.entities.Role;
import com.infodev.sanimaotp.entities.User;
import com.infodev.sanimaotp.pojo.ChangePasswordPojo;
import com.infodev.sanimaotp.pojo.RolePojo;
import com.infodev.sanimaotp.pojo.UserLogPojo;
import com.infodev.sanimaotp.pojo.UserManagement.PasswordUpdatePojo;
import com.infodev.sanimaotp.pojo.UserPojo;
import com.infodev.sanimaotp.services.adminService.userlog.UserLogService;
import com.infodev.sanimaotp.services.utils.convertors.RoleConvertor;
import com.infodev.sanimaotp.services.utils.convertors.UserConverter;
import com.infodev.sanimaotp.services.utils.security.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private static final String NO_RECORD_FOUND_BY_GIVEN_USERNAME = "no record found by given username %s";
    private final UserRepository userRepository;
    private final UserLogService _uLogService;
    private final RoleRepository roleRepository;
    @Override
    public List<UserPojo> getAllUser() {
        return Optional
                .ofNullable(userRepository.findAll())
                .map(UserConverter::userToUserPojoList)
                .orElse(null);
    }

    @Transactional
    @Override
    public void changeStatus(final String username, final Authentication authentication) {
        final String currentLoggedUsername = authentication.getName();
        //1. get user using username
        final User user = this.getUserByUsername(username);

        //2. update user to db - if 0 set 1 - if 1 set 0
        user.setUserStatus(user.getUserStatus() == 0 ? 1 : 0);

        //3. add log to userLog
        //3.1 get user details from db using current logged user
        final User currentLoggedUser = this.getUserByUsername(currentLoggedUsername);

        //3.2 save user log details to db
        _uLogService.saveUserLog(UserLogPojo
                .builder()
                .rDateTime(LocalDateTime.now())
                .taskBy(currentLoggedUser.getId())
                .taskType("enable or disable user")
                .extraInfo1(UserConverter.objectToJson(currentLoggedUser))
                .extraInfo2(UserConverter.objectToJson(user))
                .extraInfo3("")
                .build());
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public RolePojo getRoleById(int id) {
        return roleRepository.findById(id)
                .map(RoleConvertor::toPojo)
                .orElseThrow(() -> new RuntimeException("role not found with id " + id));
    }

    @Override
    public UserPojo getByUsername(final String username) {
        return userRepository
                .findByUsername(username)
                .map(UserConverter::userToUserPojo)
                .orElseThrow(() ->
                        new RuntimeException(String.format("no user found by username '%s'.", username)));
    }

    @Override
    @Transactional
    public Boolean updatePassword(PasswordUpdatePojo passwordUpdatePojo,
                                  final Authentication authentication) {

        //is password and retype pass matched?
        if (!passwordUpdatePojo.getPassword().equals(passwordUpdatePojo.getRePassword())) {
            throw new RuntimeException("Password values does not match");
        }

        final Integer userId = passwordUpdatePojo.getUserId();

        final User user = userRepository
                .findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("Invalid user id."));

        user.setPassword(SecurityUtils.passwordEncoder().encode(passwordUpdatePojo.getPassword()));
        if (userId != userRepository.findByUsername(authentication.getName()).get().getId())
            user.setRequiredPassChange(true);

        userRepository.save(user).getId();
        try{
            _uLogService.saveUserLog(UserLogPojo
                    .builder()
                    .rDateTime(LocalDateTime.now())
                    .taskBy(userRepository.findByUsername(authentication.getName()).get().getId())
                    .taskType("update user password")
                    .extraInfo1(userId.toString())
                    .extraInfo2("")
                    .extraInfo3("")
                    .build());
        }catch(Exception e){
            log.info("Failed to save log");
        }
        return true;
    }

    @Override
    public Boolean changePassword(ChangePasswordPojo changePasswordPojo, Authentication authentication) {

        final User user = userRepository.findByUsername(authentication.getName())
                        .orElseThrow(()-> new RuntimeException("user not found."));

        user.setPassword(SecurityUtils.passwordEncoder().encode(changePasswordPojo.getPassword()));
        user.setRequiredPassChange(false);

        userRepository.save(user);

        try{
            _uLogService.saveUserLog(UserLogPojo
                    .builder()
                    .rDateTime(LocalDateTime.now())
                    .taskBy(userRepository.findByUsername(authentication.getName()).get().getId())
                    .taskType("update user password")
                    .extraInfo1(new Integer(user.getId()).toString())
                    .extraInfo2("")
                    .extraInfo3("")
                    .build());
        }catch(Exception e){
            log.info("Failed to save log");
        }
        return true;
    }

    private final User getUserByUsername(final String username) {
        return Optional
                .ofNullable(userRepository.findByName(username))
                .orElseThrow(() ->
                        new RuntimeException(String.format(NO_RECORD_FOUND_BY_GIVEN_USERNAME, username)));
    }


}
