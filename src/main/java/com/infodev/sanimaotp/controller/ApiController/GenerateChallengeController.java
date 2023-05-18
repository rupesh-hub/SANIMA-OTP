package com.infodev.sanimaotp.controller.ApiController;


import com.infodev.sanimaotp.entities.DataLogger;
import com.infodev.sanimaotp.pojo.GenerateChallengePojo;
import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.services.utils.LogActivityService;
import com.infodev.sanimaotp.services.utils.ValidateRequestParameters;
import com.infodev.sanimaotp.services.apiService.generateChallenge.GenerateChallengeService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(description = "Generate challenge when application is operating on challenge response mode.")
@CrossOrigin
public class GenerateChallengeController {


    @Autowired
    @Qualifier("impl1")
    private GenerateChallengeService generateChallengeService;

    @Autowired
    private LogActivityService logActivityService;

    @RequestMapping(value = "api/generateChallenge", method = RequestMethod.POST)
    @CrossOrigin
    public ResponseEntity<GlobalResponse> generateChallenge(@Valid @RequestBody GenerateChallengePojo generateChallengePojo,
                                                            BindingResult bindingResult,
                                                            final Authentication authentication) {

        /*
        *  GlobalResponse class is used to store response status,data,message based on application result
        * */

        GlobalResponse globalResponse = new GlobalResponse();
        DataLogger dataLogger = new DataLogger("generateChallenge",generateChallengePojo.getCentralId(),generateChallengePojo.getApplicationId(),generateChallengePojo.getChannelId());
        try {
            ValidateRequestParameters.validateRequest(dataLogger,bindingResult);
            globalResponse= generateChallengeService.generateChallenge(generateChallengePojo);

        } catch (Exception e) {
            e.printStackTrace();
            globalResponse.setMessage(e.getMessage());
            globalResponse.setStatus(0);
            globalResponse.setData(null);
        }
        finally{
            dataLogger.setActivityStatus(globalResponse.getStatus());
            dataLogger.setActivityDescription(globalResponse.getMessage());
            dataLogger.setTaskBy(authentication.getName());

            if(globalResponse.getData()!=null){
                dataLogger.setExtraInfo1("challengeSent: "+globalResponse.getData().toString());
            }else{
                dataLogger.setExtraInfo1("challengeSent: null");
            }
            logActivityService.saveActivity(dataLogger);

            return new ResponseEntity<GlobalResponse>(globalResponse, HttpStatus.OK);
        }



    }
}
