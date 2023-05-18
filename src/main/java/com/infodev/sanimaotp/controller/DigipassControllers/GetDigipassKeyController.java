package com.infodev.sanimaotp.controller.DigipassControllers;

import com.infodev.sanimaotp.controller.base.BaseController;
import com.infodev.sanimaotp.enums.ModuleEnum;
import com.infodev.sanimaotp.services.apiService.QRExtracter.QRCodeReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GetDigipassKeyController extends BaseController {

    private final QRCodeReaderService qrCodeReaderService;

    @Autowired
    public GetDigipassKeyController(QRCodeReaderService qrCodeReaderService) {
        this.qrCodeReaderService = qrCodeReaderService;
        this.module1 = ModuleEnum.GET_DIGIPASS_KEY.getAbbreviation();
    }

    @GetMapping("/generateActivationCode_")
    @PreAuthorize("hasPermission(#this.this.module1,'')")
    public String getActivationCode(final Model model) {
        model.addAttribute("digipassReqList", qrCodeReaderService.findAllByApprovedStatus());
        return "pages/displayActivationCode";
    }
}
