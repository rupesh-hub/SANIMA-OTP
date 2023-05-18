package com.infodev.sanimaotp.services.utils.convertors;

import com.infodev.sanimaotp.dao.BranchRepository;
import com.infodev.sanimaotp.entities.User;
import com.infodev.sanimaotp.entities.UserRole;
import com.infodev.sanimaotp.pojo.UserPojo;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class UserConverter {


    public UserPojo userToUserPojo(final User user) {

        return UserPojo
                .builder()
                .username(user.getUsername())
                .role(getRole(user) == null ? "N.A." : getRole(user))
                .status(user.getUserStatus())
                .userId(user.getId())
                .requiredPassChange(user.isRequiredPassChange())
                .branch(user.getBranch())
                .branchName(user.getBranch() == null ? "" : user.getBranch().getName())
                .build();
    }

    public List<UserPojo> userToUserPojoList(final List<User> userList) {
        return userList
                .stream()
                .map(user -> userToUserPojo(user))
                .collect(Collectors.toList());
    }

    public String objectToJson(User user) {
        return "User{" +
                "username='" + user.getUsername() + '\'' +
                ", userStatus=" + user.getUserStatus() +
                ", userRoles=" + getRole(user) +
                '}';
    }

    private String getRole(User user) {
        String roleName = null;

        for (UserRole userRole : user.getUserRole()) {
            roleName = userRole.getRole().getName();
        }
        return roleName;
    }


}
