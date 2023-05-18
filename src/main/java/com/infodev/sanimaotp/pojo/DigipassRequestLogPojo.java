package com.infodev.sanimaotp.pojo;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DigipassRequestLogPojo {

    private Long digipassRequestId;
    private LocalDate actionDate;
    private String actionType;
    private String extraDetail1;
    private String extraDetail2;
    private String extraDetail3;

}
