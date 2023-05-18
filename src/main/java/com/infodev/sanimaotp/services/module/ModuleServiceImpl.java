package com.infodev.sanimaotp.services.module;

import com.infodev.sanimaotp.dao.ModuleRepository;
import com.infodev.sanimaotp.entities.Module;
import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.pojo.ModulePojo;
import com.infodev.sanimaotp.services.roleModule.RoleModuleService;
import com.infodev.sanimaotp.services.utils.convertors.ModuleConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    public static final String MODULE_NOT_EXISTS_MNAME = "module '%s' is not exists.";
    public static final String MODULE_UPDATED_SUCCESSFULLY = "module has been updated successfully";
    public static final String MODULE_CREATED_SUCCESSFULLY = "module '%s' has been created successfully";
    public static final String MODULE_ALREADY_EXISTS = "module '%s' is already exists.";
    public static final String MODULE_REMOVED_SUCCESSFULLY = "module removed successfully";
    private final ModuleRepository _mRepository;
    private final RoleModuleService _rModuleService;

    @Override
    @Transactional
    public GlobalResponse createModule(final ModulePojo modulePojo) {

        final String moduleName = modulePojo.getModuleName();
        modulePojo.setModuleAbbreviation(getAbbreviation(moduleName));
        //first check for conflict
        if(isModuleExists(modulePojo))
            return GlobalResponse
                    .builder()
                    .message(String.format(MODULE_ALREADY_EXISTS,moduleName))
                    .status(HttpStatus.CONFLICT.value())
                    .data(null)
                    .build();

        Module module = _mRepository.save(ModuleConvertor.toModule(modulePojo));

        return GlobalResponse
                .builder()
                .message(String.format(MODULE_CREATED_SUCCESSFULLY,moduleName))
                .status(HttpStatus.CREATED.value())
                .data(ModuleConvertor.toPojo(module))
                .build();
    }

    @Override
    @Transactional
    public GlobalResponse updateModule(final ModulePojo modulePojo, final String oldName) {
        Module module = getModuleByName(oldName)
                .orElseThrow(()->
                        new RuntimeException(String.format(MODULE_NOT_EXISTS_MNAME, oldName)));;

        module.setModuleName(modulePojo.getModuleName());
        module.setModuleAbbreviation(modulePojo.getModuleAbbreviation());

        return GlobalResponse
                .builder()
                .message(MODULE_UPDATED_SUCCESSFULLY)
                .status(modulePojo.getStatus())
                .data(modulePojo)
                .build();
    }

    @Override
    @Transactional
    public GlobalResponse deleteModule(final String moduleName) {
        Module module = _mRepository
                .getByName(moduleName)
                .orElseThrow(()-> new RuntimeException(String.format(MODULE_NOT_EXISTS_MNAME, moduleName)));

        _mRepository.delete(module);

        return GlobalResponse
                .builder()
                .message(MODULE_REMOVED_SUCCESSFULLY)
                .status(HttpStatus.OK.value())
                .data(ModuleConvertor.toPojo(module))
                .build();
    }

    @Override
    public List<ModulePojo> allModules() {
        return Optional
                .ofNullable(_mRepository.findAll())
                .map(ModuleConvertor::toPojoList)
                .orElse(new ArrayList<>());
    }

    @Override
    public List<ModulePojo> allActiveModules() {
        return Optional
                .ofNullable(_mRepository.fetchActiveModules())
                .map(ModuleConvertor::toPojoList)
                .orElse(new ArrayList<>());
    }

    @Override
    public ModulePojo getModule(final String moduleName) {
        return _mRepository
                .getByName(moduleName)
                .map(ModuleConvertor::toPojo)
                .orElseThrow(()->new RuntimeException(String.format(MODULE_NOT_EXISTS_MNAME, moduleName)));
    }

    private final Optional<Module> getModuleByName(final String moduleName){
        return _mRepository.getByName(moduleName);
    }

    private final Optional<Module> getModuleByNameAbbr(final String nameAbbr){
        return _mRepository.getByNameAbbr(nameAbbr);
    }

    private final boolean isModuleExists(final ModulePojo modulePojo) {
        if(getModuleByName(modulePojo.getModuleName()).isPresent()
                || getModuleByNameAbbr(modulePojo.getModuleAbbreviation()).isPresent())
            return true;

        return false;
    }

    private final String getAbbreviation(final String moduleName){
        StringBuilder initialLetters = new StringBuilder();
        for (String s : moduleName.split(" ")) {
            initialLetters.append(s.charAt(0));
        }
        return initialLetters.toString().toUpperCase();
    }
}
