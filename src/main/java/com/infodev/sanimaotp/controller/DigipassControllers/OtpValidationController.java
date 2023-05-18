package com.infodev.sanimaotp.controller.DigipassControllers;

import com.infodev.sanimaotp.controller.base.BaseController;
import com.infodev.sanimaotp.enums.ModuleEnum;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OtpValidationController extends BaseController {

    public OtpValidationController() {
        this.module1 = ModuleEnum.OTP_VALIDATION.getAbbreviation();
    }

    @GetMapping("/validateOTP_")
    @PreAuthorize("hasPermission(#this.this.module1,'')")
    public String validateOTP() {
        return "pages/validateOTP";
    }
}
