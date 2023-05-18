package com.infodev.sanimaotp.services.roleModule;

import com.infodev.sanimaotp.dao.ModuleRepository;
import com.infodev.sanimaotp.dao.RoleRepository;
import com.infodev.sanimaotp.entities.Module;
import com.infodev.sanimaotp.entities.Role;
import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.pojo.ModuleAssignRequest;
import com.infodev.sanimaotp.pojo.ModuleAssignResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleModuleServiceImpl implements RoleModuleService {
    private final RoleRepository roleRepository;
    private final ModuleRepository moduleRepository;

    @Override
    @Transactional
    public GlobalResponse assignModulesToRole(final ModuleAssignRequest moduleAssignRequest) {

        log.info("Role Id: {}",moduleAssignRequest.getRoleId());
        log.info("Module Size: {}",moduleAssignRequest.getModules().size());

        final List<Long> moduleIds = moduleAssignRequest.getModules();
        final Integer roleId = moduleAssignRequest.getRoleId();

        //1. get role
        final Role role = roleRepository
                .findById(roleId).orElseThrow(() ->
                        new RuntimeException(String.format("no role found with role id '%s'.", roleId)));

        //2. delete previous modules from role
        roleRepository.deleteExistingModulesFromRole(roleId);
        role.setModules(new ArrayList<>());

        //3. assign module to role
        for (Long id : moduleIds) {
            Module module = moduleRepository.findById(id).orElseThrow(() ->
                    new RuntimeException(String.format("no module found with module id '%s'.", id)));

//            //check whether module is already assigned to role
//            if(!role.getModules().contains(module))
                role.addModule(module);
        }

        roleRepository.save(role);

        return GlobalResponse
                .builder()
                .message(String.format("module assign to role '%s' success", role.getName()))
                .status(HttpStatus.OK.value())
                .data(moduleAssignRequest)
                .build();
    }


    @Override
    public List<ModuleAssignResponse> allRoleModules() {
        return roleRepository
                .findAll()
                .stream()
                .map(roleModule -> this.toResponse(roleModule))
                .collect(Collectors.toList());
    }

    @Override
    public Role roleModuleByRoleId(Integer roleId) {
        Optional<Role> role = roleRepository
                .findById(roleId);
        return role
                .orElseThrow(() ->
                        new RuntimeException(String.format("no record available with id %s", roleId)));
    }

    private ModuleAssignResponse toResponse(final Role role) {
        List<String> moduleAbbrList = new ArrayList<>();
        for (Module module : role.getModules())
            moduleAbbrList.add(module.getModuleAbbreviation());

        return ModuleAssignResponse
                .builder()
                .roleId(role.getId())
                .roleName(role.getName())
                .moduleAbbrList(moduleAbbrList)
                .build();
    }

}
