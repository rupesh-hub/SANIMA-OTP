package com.infodev.sanimaotp.pojo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DigipassRequestPojo {

    private Long id;
    private String centralId;
    private String applicationId;
    private String applicationType;
    private int status;
    private String approvalStatus;//PENDING/APPROVE/REJECT
    private String remarks;
    private int toBeApprovedBy;
    private boolean qrGeneratedStatus;

}
