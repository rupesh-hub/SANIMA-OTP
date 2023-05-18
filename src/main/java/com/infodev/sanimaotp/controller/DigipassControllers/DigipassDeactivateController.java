package com.infodev.sanimaotp.controller.DigipassControllers;

import com.infodev.sanimaotp.controller.base.BaseController;
import com.infodev.sanimaotp.enums.ModuleEnum;
import com.infodev.sanimaotp.services.adminService.deactivateDigipass.DeactivateDigipassService;
import com.infodev.sanimaotp.services.utils.LogActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class DigipassDeactivateController extends BaseController {

    private final DeactivateDigipassService deactivateDigipassService;
    private final LogActivityService logActivityService;

    @Autowired
    public DigipassDeactivateController(DeactivateDigipassService deactivateDigipassService, LogActivityService logActivityService){
        this.deactivateDigipassService = deactivateDigipassService;
        this.logActivityService = logActivityService;
        this.module1 = ModuleEnum.DEACTIVATE_DIGIPASS.getAbbreviation();
    }

    @GetMapping("/deactivateDigipass_")
    @PreAuthorize("hasPermission(#this.this.module1,'')")
    public String deactivateDigipass() {
        return "pages/deactivateDigipass";
    }
}
