package com.infodev.sanimaotp.exception;


import com.infodev.sanimaotp.pojo.GlobalResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by abin on 9/13/2018.
 */
@ControllerAdvice
public class ExceptionController extends RuntimeException {
    private final Logger logger= LoggerFactory.getLogger(LoggerFactory.class);
    @ExceptionHandler(value = AppBlobException.class)
    public ResponseEntity<GlobalResponse> handleNullException(HttpServletRequest request, NullPointerException ex){

        logger.error("Request" +request.getRequestURL() +  " Threw an Exception ", ex);
        GlobalResponse globalResponse= new GlobalResponse();
        globalResponse.setStatus(0);
        globalResponse.setMessage("ERROR:: " +ex.getMessage());
        globalResponse.setData(null);
        return new ResponseEntity<GlobalResponse>(globalResponse, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<GlobalResponse> handleException(HttpServletRequest request, Exception ex){
        ex.printStackTrace();
        logger.error("Request "+request.getRequestURL() + " Threw an exception " +ex.getMessage());
        GlobalResponse globalResponse= new GlobalResponse();
        globalResponse.setStatus(0);
        globalResponse.setMessage(ex.getMessage());
        globalResponse.setData(null);
        return new ResponseEntity<GlobalResponse>(globalResponse, HttpStatus.OK);
    }

    @ExceptionHandler(value=DpxImportException.class)
    public String handleDpxImportException(Exception e,Model model){
      model.addAttribute("status",false);
      model.addAttribute("message",e.getMessage());
      return "pages/dpximport";
    }

}
