package com.infodev.sanimaotp.controller.ApiController;

import com.infodev.sanimaotp.dao.DigipassStatusRepository;
import com.infodev.sanimaotp.entities.DataLogger;
import com.infodev.sanimaotp.entities.DigipassStatus;
import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.pojo.IdentifyUserPojo;
import com.infodev.sanimaotp.services.utils.LogActivityService;
import com.infodev.sanimaotp.services.utils.SetGetAppIdInStatus;
import com.infodev.sanimaotp.services.utils.ValidateRequestParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
public class CheckDigipassStatus {

    @Autowired
    private DigipassStatusRepository digipassStatusRepository;

    @Autowired
    private LogActivityService logActivityService;

    private Logger logger= LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "api/checkDigipassStatus", method = RequestMethod.POST)
    public ResponseEntity<GlobalResponse> checkStatus(@RequestBody @Valid IdentifyUserPojo identifyUserPojo,
                                                      BindingResult bindingResult,
                                                      final Authentication authentication) {
        List<Map<Object, Object>> list = new ArrayList<>();
        DigipassStatus digipassStatus = digipassStatusRepository.getStatusRowByCentralId(identifyUserPojo.getCentralId());
        Map map= new LinkedHashMap();
        GlobalResponse globalResponse=new GlobalResponse();
        DataLogger dataLogger = new DataLogger("checkDigipassStatus",identifyUserPojo.getCentralId(),identifyUserPojo.getApplicationId(),identifyUserPojo.getChannelId());
        try {
            ValidateRequestParameters.validateRequest(dataLogger,bindingResult);
            if(identifyUserPojo.getChannelId().equals("getAll")){
                map.put("centralId",digipassStatus.getCentralId());
                map.put("APP1_MB",digipassStatus.getApp1Mb());
                map.put("APP2_KIOSK",digipassStatus.getApp2Kiosk());
                map.put("APP3_ATM",digipassStatus.getApp3Atm());
                map.put("APP4_VBV",digipassStatus.getApp4Vbv());
                map.put("APP5_IBANK",digipassStatus.getApp5Ibank());
                map.put("APP6_VBV_CR",digipassStatus.getApp6VbvCr());
                map.put("APP7_KIOSKCR",digipassStatus.getApp7Kioskcr());
                map.put("APP8_FT_SIG",digipassStatus.getApp8FtSig());
                map.put("serialKey",digipassStatus.getSerialNumber());
                map.put("status",digipassStatus.getStatus());
            }else{
                String appValue = new SetGetAppIdInStatus(identifyUserPojo.getChannelId(), identifyUserPojo.getApplicationId()).getAppId(digipassStatus);
                map.put("applicationId",appValue);
                map.put("centralId",digipassStatus.getCentralId());
                map.put("channelId", identifyUserPojo.getChannelId());
                map.put("serialKey",digipassStatus.getSerialNumber());
                map.put("status",digipassStatus.getStatus());
            }
            list.add(map);
            globalResponse.setData(list);
            globalResponse.setStatus(1);
            globalResponse.setMessage("Data found successfully");
        } catch (Exception e) {
            globalResponse.setStatus(0);
            globalResponse.setData(null);
            globalResponse.setMessage(e.getMessage());
        } finally {
            dataLogger.setActivityStatus(globalResponse.getStatus());
            dataLogger.setActivityDescription(globalResponse.getMessage());
            dataLogger.setTaskBy(authentication.getName());
            logActivityService.saveActivity(dataLogger);
            return new ResponseEntity(globalResponse, HttpStatus.OK);
        }


    }
}
