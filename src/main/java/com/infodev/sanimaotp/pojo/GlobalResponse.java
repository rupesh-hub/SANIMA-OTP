package com.infodev.sanimaotp.pojo;

import com.infodev.sanimaotp.entities.DataLogger;
import lombok.Builder;
import org.springframework.stereotype.Component;

@Component
@Builder
public class GlobalResponse {
    private int status;
    private Object data;
    private String message;


    public GlobalResponse() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public GlobalResponse(int status, Object data, String message) {

        this.status = status;
        this.data = data;
        this.message = message;
    }

    public GlobalResponse(int status, Object data, String message, DataLogger dataLogger) {
        this.status = status;
        this.data = data;
        this.message = message;
    }
}
