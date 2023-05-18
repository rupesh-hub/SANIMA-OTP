package com.infodev.sanimaotp.controller.AdminController;

import com.infodev.sanimaotp.entities.DataLogger;
import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.pojo.IdentifyUserPojo;
import com.infodev.sanimaotp.services.utils.ValidateRequestParameters;
import com.infodev.sanimaotp.services.adminService.resetDigipass.ResetDigipassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@CrossOrigin
public class ResetDigipassController {
    @Autowired
    private ResetDigipassService resetDigipassService;

    @RequestMapping(value = "/api/resetDigipassblob",method= RequestMethod.POST)
    public ResponseEntity<GlobalResponse> resetDigipassBlob(@Valid @RequestBody IdentifyUserPojo identifyUserPojo, BindingResult bindingResult){
        GlobalResponse globalResponse=new GlobalResponse();
        DataLogger dataLogger=new DataLogger();
        try {
            ValidateRequestParameters.validateRequest(dataLogger,bindingResult);
           globalResponse=resetDigipassService.resetToken(identifyUserPojo);
        } catch (Exception e) {
            globalResponse.setStatus(0);
            globalResponse.setData(null);
            globalResponse.setMessage(e.getMessage());
            e.printStackTrace();
        } finally {
            return new ResponseEntity<GlobalResponse>(globalResponse, HttpStatus.OK);
        }
    }
}
