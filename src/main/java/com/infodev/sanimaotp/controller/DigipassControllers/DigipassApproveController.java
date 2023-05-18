package com.infodev.sanimaotp.controller.DigipassControllers;

import com.infodev.sanimaotp.controller.base.BaseController;
import com.infodev.sanimaotp.enums.ModuleEnum;
import com.infodev.sanimaotp.services.apiService.QRExtracter.QRCodeReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/digipassApprove_")
public class DigipassApproveController extends BaseController {

    private final QRCodeReaderService qrCodeReaderService;

    @Autowired
    public DigipassApproveController(QRCodeReaderService qrCodeReaderService) {
        this.qrCodeReaderService = qrCodeReaderService;
        this.module1 = ModuleEnum.DIGIPASS_APPROVE.getAbbreviation();
    }

    @GetMapping
    @PreAuthorize("hasPermission(#this.this.module1,'')")
    public String approvePage(final Model model,
                              final Authentication authentication) {
//        model.addAttribute("digipassReqList", qrCodeReaderService.findAllDigipass(authentication));
        return "pages/otpValidation/otp-validation-response";
    }

    @ResponseBody
    @GetMapping("/digpass-approve/list")
    public ResponseEntity<?> digpassPagination(@RequestParam(name="start") final int offset,
                                               @RequestParam(name="length", defaultValue = "10") final int limit,
                                               @RequestParam(name="search[value]", defaultValue = "") final String searchKey,
                                               @RequestParam(name="draw", defaultValue = "0") final int draw,
                                               final Authentication authentication) {
        return ResponseEntity.ok(qrCodeReaderService.findAllDigipass2(authentication,offset,limit,searchKey,draw));
    }

    @GetMapping("/approve/{id}")
    public String approveRequest(@PathVariable final Long id) {
        qrCodeReaderService.approveDigipassRequest(id);
        return "redirect:/digipassApprove_";
    }

    @GetMapping("/reject/{id}/")
    public String rejectRequest(@PathVariable final Long id) {
        qrCodeReaderService.rejectDigipassRequest(id, "remarks");
        return "redirect:/digipassApprove_";
    }

}
