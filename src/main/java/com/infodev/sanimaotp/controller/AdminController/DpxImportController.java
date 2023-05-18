package com.infodev.sanimaotp.controller.AdminController;

import com.infodev.sanimaotp.entities.DataLogger;
import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.services.adminService.dpximport.ImportDpxService;
import com.infodev.sanimaotp.services.utils.LogActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;


@RestController
public class DpxImportController {

    @Autowired
    private ImportDpxService importDpxService;

    @Autowired
    private LogActivityService logActivityService;


    @RequestMapping(value = "/api/dpxImportAPI", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    private ResponseEntity<GlobalResponse> initialDPXimport(@RequestParam(value = "dpxFile") MultipartFile dpxFile,
                                                            @RequestParam(value = "InitKey") String InitKey,
                                                            final Authentication authentication) {
        GlobalResponse globalResponse = new GlobalResponse();
        DataLogger dataLogger = new DataLogger("dpxImportAPI");
        try {
            globalResponse = importDpxService.importDpxService(dpxFile, InitKey);
            if (globalResponse.getStatus() == 1) {
            } else {
            }
        } catch (Exception e) {
            globalResponse.setMessage(e.getLocalizedMessage());
            e.printStackTrace();
            globalResponse.setStatus(0);
            globalResponse.setData(null);
        } finally {
            dataLogger.setActivityStatus(globalResponse.getStatus());
            dataLogger.setActivityDescription(globalResponse.getMessage());
            dataLogger.setExtraInfo1("transportKey:" + InitKey);
            dataLogger.setTaskBy(authentication.getName());
            logActivityService.saveActivity(dataLogger);
            return new ResponseEntity(globalResponse, HttpStatus.OK);
        }
    }
}


