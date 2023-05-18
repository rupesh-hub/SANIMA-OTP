package com.infodev.sanimaotp.pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OTPGlobalResponse {

    private OTPResponse data;
    private String message;

}
