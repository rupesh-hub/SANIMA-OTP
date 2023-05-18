package com.infodev.sanimaotp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum DigipassApprovalStatus {

    PENDING("PENDING"),
    APPROVED("APPROVED"),
    REJECTED("REJECTED"),
    DEACTIVATED("DEACTIVATED");

    private String status;

}
