package com.infodev.sanimaotp.controller.DigipassControllers;

import com.infodev.sanimaotp.controller.base.BaseController;
import com.infodev.sanimaotp.enums.ModuleEnum;
import com.infodev.sanimaotp.pojo.DigipassRequestPojo;
import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.services.apiService.QRExtracter.QRCodeReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/digipassRequest_")
public class DigipassRequestController extends BaseController {

    private final QRCodeReaderService qrCodeReaderService;

    @Autowired
    public DigipassRequestController(QRCodeReaderService qrCodeReaderService) {
        this.qrCodeReaderService = qrCodeReaderService;
        this.module1 = ModuleEnum.DIGIPASS_REQUEST.getAbbreviation();
    }

    @GetMapping
    @PreAuthorize("hasPermission(#this.this.module1,'')")
    public String requestPage(final Model model,
                              final Authentication authentication) {

        model.addAttribute("appList",
                new String[]{"APP1_MB", "APP2_KIOSK", "APP3_ATM", "APP4_VBV", "APP5_IBANK", "APP6_VBV_CR", "APP7_KIOSKCR", "APP8_FT_SIG"});
        model.addAttribute("digipassReqList", qrCodeReaderService.digipassPendingList(authentication));
        return "pages/otpValidation/otp-validation-request";
    }

    @PostMapping("/generate-activation-code")
    public ResponseEntity<GlobalResponse> generateActivationCode(@RequestBody DigipassRequestPojo _dRequestPojo,
                                                                 final Authentication authentication) {
        return new ResponseEntity<>(qrCodeReaderService.digipassApproveRequest(_dRequestPojo, authentication), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DigipassRequestPojo> getActivationCode(@PathVariable final Long id) {
        return new ResponseEntity<>(qrCodeReaderService.getById(id), HttpStatus.OK);
    }

    @GetMapping("/delete/{id}")
    public String deleteActivationCode(@PathVariable final Long id){
        qrCodeReaderService.deleteDigipassRequest(id);
        return "redirect:/digipassRequest_";
    }
}
