package com.infodev.sanimaotp.controller.moduleController;

import com.infodev.sanimaotp.controller.base.BaseController;
import com.infodev.sanimaotp.entities.Module;
import com.infodev.sanimaotp.enums.ModuleEnum;
import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.pojo.ModulePojo;
import com.infodev.sanimaotp.services.module.ModuleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/module_")
public class ModuleController extends BaseController {

    private final ModuleService _mService;

    public ModuleController(ModuleService mService) {
        _mService = mService;
        this.module1 = ModuleEnum.MODULE_MANAGEMENT.getAbbreviation();
    }

    //0. get page - get list module
    @PreAuthorize("hasPermission(#this.this.module1,'')")
    @GetMapping(path = "/")
    public String getPage(final Model model) {
        model.addAttribute("modules", _mService.allModules());
        model.addAttribute("module", new Module());
        return "pages/moduleManagement/module-mgmt";
    }


    //2. update module
    @PutMapping(path = "/update/{oldName}")
    public final String updateModule(@RequestBody final ModulePojo modulePojo,
                                     @PathVariable final String oldName) {
        _mService.updateModule(modulePojo, oldName);
        return "redirect:/";
    }

    //3. delete module
    @DeleteMapping(path = "/delete/{moduleName}")
    public final String deleteModule(@PathVariable final String moduleName) {
        _mService.deleteModule(moduleName);
        return "redirect:/";
    }

    //4. get by name
    @GetMapping(path = "/{moduleName}")
    public final String getByName(@PathVariable final String moduleName,
                                  final Model model) {
        model.addAttribute("module", _mService.getModule(moduleName));
        return "redirect:/";
    }

    @PostMapping(path = "/")
    public ResponseEntity<GlobalResponse> addModule(@RequestBody final ModulePojo modulePojo) {
        return new ResponseEntity<>(_mService.createModule(modulePojo), HttpStatus.OK);
    }

}
