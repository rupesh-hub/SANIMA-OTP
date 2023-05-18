package com.infodev.sanimaotp.controller.ApiController;

import com.infodev.sanimaotp.entities.DataLogger;
import com.infodev.sanimaotp.pojo.ActivateDigipassPojo;
import com.infodev.sanimaotp.pojo.DigipassRequestPojo;
import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.services.apiService.QRExtracter.QRCodeReaderService;
import com.infodev.sanimaotp.services.utils.LogActivityService;
import com.infodev.sanimaotp.services.utils.ValidateRequestParameters;
import com.infodev.sanimaotp.services.apiService.activateDigipass.ActivateDigipassService;
import com.vasco.utils.AAL2Wrap;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@CrossOrigin
@Api(description = "Activates serial key on backend with generated derivation key from mobile application")
public class DigipassActivationController {


    @Autowired
    private ActivateDigipassService activateDigipassService;

    @Autowired
    private LogActivityService logActivityService;

    @Autowired
    private QRCodeReaderService qrCodeReaderService;

    @RequestMapping(value = "api/activateSerialKey", method = RequestMethod.POST)
    @CrossOrigin
    @ApiOperation(value="Activate serial key on backend")
    public ResponseEntity<GlobalResponse> generateActivationCode(@Valid @RequestBody ActivateDigipassPojo activateDigipassPojo,
                                                                 BindingResult bindingResult,
                                                                 final Authentication authentication) {
             GlobalResponse response=new GlobalResponse();
             DataLogger dataLogger = new DataLogger("activateSerialKey",activateDigipassPojo.getCentralId(),activateDigipassPojo.getApplicationId(),activateDigipassPojo.getChannelId());
             try{
                 ValidateRequestParameters.validateRequest(dataLogger,bindingResult);
                 response=activateDigipassService.enterDerivationKey(activateDigipassPojo);
             }
             catch(Exception e){
                 response.setStatus(0);
                 response.setData(null);
                 response.setMessage(e.getMessage());
                 System.out.println("Failure in controller...");
                 System.out.println(e.getMessage());
             }
             finally{
                 dataLogger.setActivityStatus(response.getStatus());
                 dataLogger.setActivityDescription(response.getMessage());
                 dataLogger.setExtraInfo1("derivationCode:"+activateDigipassPojo.getDerivationCode());
                 dataLogger.setTaskBy(authentication.getName());
                 logActivityService.saveActivity(dataLogger);
                 return new ResponseEntity<GlobalResponse>(response,HttpStatus.OK);
             }


    }

    /**
     * api to generate and approve activation code (digipass request and approve response)
     * @param _dRequestPojo
     * @param authentication
     * @return
     */
    @PostMapping("api/generate-approve-activation-code")
    @CrossOrigin
    public ResponseEntity<GlobalResponse> generateAndApproveDigipass(@RequestBody DigipassRequestPojo _dRequestPojo,
                                                                     final Authentication authentication) {
        return new ResponseEntity<>(qrCodeReaderService.digipassRequestAndApprove(_dRequestPojo, authentication), HttpStatus.OK);
    }
}
