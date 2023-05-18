package com.infodev.sanimaotp.controller;

import com.infodev.sanimaotp.exception.ChannelNotFoundException;
import com.infodev.sanimaotp.pojo.GlobalResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
public class ChannelValidationController {
    @RequestMapping(value = "/activation/{channel}", method = RequestMethod.GET)
    public ResponseEntity<GlobalResponse> activateUser(@PathVariable("channel") String channel) {
        GlobalResponse response= new GlobalResponse();
        try{
                switch(channel){
                    case "mb" :{ break; }
                    case "kiosk" :{ break; }
                    case "atm" :{ break; }
                    case "vbv" :{ break; }
                    case "ibank" :{ break; }
                    case "vbv_cr" :{ break; }
                    case "kiokskcr" :{ break; }
                    case "ft_sig" :{ break; }
                    default:{
                        throw new ChannelNotFoundException("Activation path not found !!");
                    }

                }
        }
        catch(ChannelNotFoundException e){
            response.setData(null);
            response.setStatus(0);
            response.setMessage(e.getMessage());
        }
        catch(Exception e){
            response.setData(null);
            response.setStatus(0);
            response.setMessage(e.getMessage());
        }
        return  new ResponseEntity<GlobalResponse>(response, HttpStatus.OK);

    }

}
