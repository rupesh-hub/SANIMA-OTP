package com.infodev.sanimaotp.controller.WebViewController;

import com.infodev.sanimaotp.entities.DataLogger;
import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.pojo.IdentifyUserPojo;
import com.infodev.sanimaotp.services.apiService.QRExtracter.QRCodeReaderService;
import com.infodev.sanimaotp.services.utils.LogActivityService;
import com.infodev.sanimaotp.services.utils.ValidateRequestParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class SendSerialKey {
    @Autowired
    private QRCodeReaderService qrCodeReaderService;


    @Autowired
    private LogActivityService logActivityService;


    @PostMapping("/view/getSerialKey")
    public String provideActivationCode(@Valid @ModelAttribute IdentifyUserPojo identifyUserPojo,
                                        BindingResult bindingResult,
                                        Model model,
                                        final Authentication authentication){
        GlobalResponse globalResponse= new GlobalResponse();
        DataLogger dataLogger = new DataLogger("getActivationCode",identifyUserPojo.getCentralId(),identifyUserPojo.getApplicationId(),identifyUserPojo.getChannelId());;
        try {
            ValidateRequestParameters.validateRequest(dataLogger,bindingResult);
            Map<String,Object> data=qrCodeReaderService.getQrImage(identifyUserPojo);
            model.addAttribute("sk",data.get("serialKey"));
            model.addAttribute("ac",data.get("activationCode"));
            model.addAttribute("url",data.get("url"));
            model.addAttribute("status",true);
            model.addAttribute("channelId",identifyUserPojo.getChannelId());
            model.addAttribute("centralId",identifyUserPojo.getCentralId().replaceAll("^\"|\"$", ""));
            model.addAttribute("applicationId",identifyUserPojo.getApplicationId().replaceAll("^\"|\"$", ""));
            dataLogger.setExtraInfo1(data.toString());

        } catch (Exception e) {
            model.addAttribute("message",e.getMessage());
            model.addAttribute("status",false);
        } finally {
            dataLogger.setActivityStatus(globalResponse.getStatus());
            dataLogger.setActivityDescription(globalResponse.getMessage());
            dataLogger.setTaskBy(authentication.getName());

            logActivityService.saveActivity(dataLogger);
            return "pages/webview/provideSerialKey";
            //return new ResponseEntity<GlobalResponse>(globalResponse, HttpStatus.OK);
        }



    }

}
