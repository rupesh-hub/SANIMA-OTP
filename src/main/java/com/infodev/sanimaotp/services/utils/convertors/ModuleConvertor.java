package com.infodev.sanimaotp.services.utils.convertors;

import com.infodev.sanimaotp.entities.Module;
import com.infodev.sanimaotp.pojo.ModulePojo;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class ModuleConvertor {
    
    public Module toModule(ModulePojo modulePojo) {
        return Module
                .builder()
                .moduleName(modulePojo.getModuleName())
                .moduleAbbreviation(modulePojo.getModuleAbbreviation())
                .remarks(modulePojo.getRemarks())
                .status(modulePojo.getStatus())
                .build();
    }
    
    public ModulePojo toPojo(Module module) {
        return ModulePojo
                .builder()
                .id(module.getId())
                .moduleName(module.getModuleName())
                .moduleAbbreviation(module.getModuleAbbreviation())
                .remarks(module.getRemarks())
                .status(module.getStatus())
                .build();
    }
    
    public List<ModulePojo> toPojoList(List<Module> modules) {
        return modules
                .stream()
                .map(module -> toPojo(module))
                .collect(Collectors.toList());
    }
    
}
