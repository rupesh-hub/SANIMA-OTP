package com.infodev.sanimaotp.pojo;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserLogResponse {

    private LocalDateTime rDateTime;
    private String taskBy;
    private String taskType;
    private String extraInfo1;
    private String extraInfo2;
    private String extraInfo3;

}
