package com.infodev.sanimaotp.services.utils.convertors;

import com.infodev.sanimaotp.entities.Role;
import com.infodev.sanimaotp.pojo.RolePojo;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RoleConvertor {

    public RolePojo toPojo(final Role role) {
        return RolePojo
                .builder()
                .id(role.getId())
                .name(role.getName())
                .description(role.getDescription())
                .build();
    }
}
