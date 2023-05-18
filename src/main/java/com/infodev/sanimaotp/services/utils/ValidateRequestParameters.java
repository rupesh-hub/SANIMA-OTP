package com.infodev.sanimaotp.services.utils;

import com.infodev.sanimaotp.entities.DataLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
@Slf4j
public class ValidateRequestParameters{

    public static void validateRequest(DataLogger dataLogger,BindingResult bindingResult) throws Exception {
        try {
            log.info("Validating request body");
            if(bindingResult.hasErrors()){
                String errorFields=" ";

                List<FieldError> errors = bindingResult.getFieldErrors();
                for (FieldError error : errors ) {
                    errorFields=errorFields+" "+(error.getField() + " - " + error.getDefaultMessage()+" ,");
                }
                dataLogger.setExtraInfo3("request parameter error");
                throw new Exception("Request parameter error :"+errorFields);
            }
        } catch (Exception e) {
            log.error("Error while validating request body");
            throw new Exception(e.getMessage());
        }
    }
}
