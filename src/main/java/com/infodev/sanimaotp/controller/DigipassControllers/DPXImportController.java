package com.infodev.sanimaotp.controller.DigipassControllers;

import com.infodev.sanimaotp.controller.base.BaseController;
import com.infodev.sanimaotp.enums.ModuleEnum;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DPXImportController extends BaseController {

    public DPXImportController(){
        this.module1 = ModuleEnum.DPX_IMPORT.getAbbreviation();
    }

    @GetMapping("admin/dpxImport_")
    @PreAuthorize("hasPermission(#this.this.module1,'')")
    public String dpxImport() {
        return "pages/dpxImport";
    }
}
