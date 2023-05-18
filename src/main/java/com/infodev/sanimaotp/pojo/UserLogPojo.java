package com.infodev.sanimaotp.pojo;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserLogPojo {

    private LocalDateTime rDateTime;
    private int taskBy;
    private String taskType;
    private String extraInfo1;
    private String extraInfo2;
    private String extraInfo3;

}
