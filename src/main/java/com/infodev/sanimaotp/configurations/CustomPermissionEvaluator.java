package com.infodev.sanimaotp.configurations;

import com.infodev.sanimaotp.dao.RoleRepository;
import com.infodev.sanimaotp.dao.UserRepository;
import com.infodev.sanimaotp.entities.Module;
import com.infodev.sanimaotp.entities.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class CustomPermissionEvaluator implements PermissionEvaluator {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public boolean hasPermission(
            Authentication auth, Object targetDomainObject, Object permission) {
        if ((auth == null) || (targetDomainObject == null) || !(permission instanceof String)) {
            return false;
        }
        String targetType = targetDomainObject.toString().toUpperCase();
        return hasPrivilege(auth, targetType, permission.toString());
    }

    @Override
    public boolean hasPermission(
            Authentication auth, Serializable targetId, String targetType, Object permission) {
        if ((auth == null) || (targetType == null) || !(permission instanceof String)) {
            return false;
        }
        return hasPrivilege(auth, targetType,
                permission.toString());
    }

    private boolean hasPrivilege(Authentication auth, String targetType, String permission) {
        Boolean status = Boolean.FALSE;

        /**
         * Getting all module List assigned to user
         */
        final String username = auth.getName();

        final Role role = roleRepository
                .getRoleByUsername(username)
                .orElseThrow(() -> new RuntimeException(String.format("no record found by username '%s'", username)));

        final List<String> moduleList =
                role
                        .getModules()
                        .stream()
                        .map(module -> module.getModuleAbbreviation())
                        .collect(Collectors.toList());

        final List<String> targetList = Arrays.asList(targetType.split("#"));

        /*Checking if assigned modules contains target module*/
        if (moduleList.size() > 0) {
            for (String moduleName : moduleList) {
                for (String targetName : targetList) {
                    if (moduleName.equalsIgnoreCase(targetName)) {
                        status = Boolean.TRUE;
                        break;
                    }
                }
                if (status.equals(Boolean.TRUE))
                    break;
            }
        }
        return status;
    }
}