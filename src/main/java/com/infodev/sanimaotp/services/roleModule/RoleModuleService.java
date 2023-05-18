package com.infodev.sanimaotp.services.roleModule;

import com.infodev.sanimaotp.entities.Role;
import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.pojo.ModuleAssignRequest;
import com.infodev.sanimaotp.pojo.ModuleAssignResponse;

import java.util.List;

public interface RoleModuleService {

    GlobalResponse assignModulesToRole(ModuleAssignRequest moduleAssignRequest);
    List<ModuleAssignResponse> allRoleModules();
    Role roleModuleByRoleId(Integer roleId);

}
