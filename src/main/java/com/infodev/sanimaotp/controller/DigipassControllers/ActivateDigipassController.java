package com.infodev.sanimaotp.controller.DigipassControllers;

import com.infodev.sanimaotp.controller.base.BaseController;
import com.infodev.sanimaotp.enums.ModuleEnum;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ActivateDigipassController extends BaseController {

    public ActivateDigipassController(){
        this.module1 = ModuleEnum.ACTIVATE_DIGIPASS.getAbbreviation();
    }


    @GetMapping("/activateDigipass_")
    @PreAuthorize("hasPermission(#this.this.module1,'')")
    public String activateDigpass() {
        return "pages/activateDigipass";
    }
}
