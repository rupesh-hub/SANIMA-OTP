package com.infodev.sanimaotp.controller.WebViewController;

import com.infodev.sanimaotp.entities.DataLogger;
import com.infodev.sanimaotp.pojo.ActivateDigipassPojo;
import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.services.utils.LogActivityService;
import com.infodev.sanimaotp.services.utils.ValidateRequestParameters;
import com.infodev.sanimaotp.services.apiService.activateDigipass.ActivateDigipassService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;


@Controller
@Api(description = "Activates serial key on backend with generated derivation key from mobile application")
public class GetDerivationCode {


    @Autowired
    private ActivateDigipassService activateDigipassService;

    @Autowired
    private LogActivityService logActivityService;

    @RequestMapping(value = "view/activateSerialKey", method = RequestMethod.POST)
    public String generateActivationCode(@Valid @ModelAttribute ActivateDigipassPojo activateDigipassPojo,
                                         BindingResult bindingResult,
                                         Model model,
                                         final Authentication authentication) {
        GlobalResponse response=new GlobalResponse();
        DataLogger dataLogger = new DataLogger("activateSerialKey",activateDigipassPojo.getCentralId(),activateDigipassPojo.getApplicationId(),activateDigipassPojo.getChannelId());
        try{
            ValidateRequestParameters.validateRequest(dataLogger,bindingResult);
            response=activateDigipassService.enterDerivationKey(activateDigipassPojo);
            model.addAttribute("status",false);
            model.addAttribute("activationStatus",true);
            model.addAttribute("message",response.getMessage());
        }
        catch(Exception e){
            model.addAttribute("channelId",activateDigipassPojo.getChannelId().replaceAll("^\"|\"$", ""));
            model.addAttribute("applicationId",activateDigipassPojo.getApplicationId().replaceAll("^\"|\"$", ""));
            model.addAttribute("centralId",activateDigipassPojo.getCentralId().replaceAll("^\"|\"$", ""));
            model.addAttribute("status",false);
            model.addAttribute("message",e.getMessage());
        }
        finally{
            dataLogger.setActivityStatus(response.getStatus());
            dataLogger.setActivityDescription(response.getMessage());
            dataLogger.setExtraInfo1("derivationCode:"+activateDigipassPojo.getDerivationCode());
            dataLogger.setTaskBy(authentication.getName());
            logActivityService.saveActivity(dataLogger);
            return "pages/webview/provideSerialKey";
        }


    }

    @GetMapping("/inputPage/GetSerialKey")
    public String getCode(Model model){

        return "pages/webview/inputPage/GetSerialKey";
    }
}
