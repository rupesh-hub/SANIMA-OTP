package com.infodev.sanimaotp.services.module;

import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.pojo.ModulePojo;

import java.util.List;

public interface ModuleService {

    //1.create
    GlobalResponse createModule(ModulePojo modulePojo);
    //2.update
    GlobalResponse updateModule(ModulePojo modulePojo, String oldName);
    //3.delete
    GlobalResponse deleteModule(String moduleName);
    //4. get all
    List<ModulePojo> allModules();


    List<ModulePojo> allActiveModules();
    //5. get by name/name_abbr
    ModulePojo getModule(String moduleName);
}
