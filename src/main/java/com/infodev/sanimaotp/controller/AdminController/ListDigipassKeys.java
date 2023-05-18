package com.infodev.sanimaotp.controller.AdminController;

import com.infodev.sanimaotp.entities.DigipassStatus;
import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.services.adminService.ListDigipassKeysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class ListDigipassKeys {

    @Autowired
    private ListDigipassKeysService listDigipassKeysService;


    @RequestMapping(value="api/listKeys",method= RequestMethod.POST)
    public ResponseEntity<GlobalResponse> listDigipassKeys(){
        GlobalResponse globalResponse = new GlobalResponse();
       try{
           List<DigipassStatus> res = listDigipassKeysService.getDigipassList();
           globalResponse.setData(res);
           globalResponse.setStatus(1);
           globalResponse.setMessage("Keys list retrieved successfully.");
       }catch(Exception e){
           globalResponse.setData(null);
           globalResponse.setStatus(0);
           globalResponse.setMessage(e.getMessage());
       }finally{
           return new ResponseEntity<GlobalResponse>(globalResponse, HttpStatus.OK);
       }

    }

    @RequestMapping(value="api/getStats")
    public ResponseEntity<GlobalResponse> getStats(){
        GlobalResponse globalResponse = new GlobalResponse();
        try{
            globalResponse.setData(listDigipassKeysService.getDashboardData());
            globalResponse.setStatus(1);
            globalResponse.setMessage("Key stats retrieved successfully.");
        }catch(Exception e){
            globalResponse.setData(null);
            globalResponse.setStatus(0);
            globalResponse.setMessage(e.getMessage());
        }finally{
            return new ResponseEntity<GlobalResponse>(globalResponse, HttpStatus.OK);
        }
    }
}











