package com.infodev.sanimaotp.controller.AdminController;

import com.infodev.sanimaotp.entities.DataLogger;
import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.pojo.IdentifyUserPojo;
import com.infodev.sanimaotp.services.adminService.deactivateDigipass.DeactivateDigipassService;
import com.infodev.sanimaotp.services.utils.LogActivityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@Api(description = "Deactivates serial key on backend using centralId")
public class DeactivateDigipassController {

    @Autowired
    DeactivateDigipassService deactivateDigipassService;
    @Autowired
    private LogActivityService logActivityService;

    @PostMapping("/api/deactivateSerialKey")
    @CrossOrigin
    @ApiOperation(value="Deactivate serial key on backend")
    public GlobalResponse deactivateDigipass(@RequestBody IdentifyUserPojo identifyUserPojo,
                                             final Authentication authentication){

        GlobalResponse globalResponse=new GlobalResponse();
        DataLogger dataLogger = new DataLogger("deactivateSerialKey",identifyUserPojo.getCentralId(),null,null);
        try{
            globalResponse = deactivateDigipassService.deactivateDigipass(identifyUserPojo);
        }catch (Exception e){
            e.printStackTrace();
            globalResponse = new GlobalResponse(0,null,e.getMessage());
        }finally {
            dataLogger.setActivityStatus(globalResponse.getStatus());
            dataLogger.setActivityDescription(globalResponse.getMessage());
            dataLogger.setExtraInfo1("centralId: "+identifyUserPojo.getCentralId());
            dataLogger.setTaskBy(authentication.getName());
            logActivityService.saveActivity(dataLogger);
            return globalResponse;
        }

    }
}
