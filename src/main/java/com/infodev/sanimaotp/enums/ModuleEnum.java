package com.infodev.sanimaotp.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Ablok Man Shakya
 * @version 1.0
 * @since 5/20/22
 */

@AllArgsConstructor
@Getter
public enum ModuleEnum {

    DASHBOARD("D"),
    MODULE_MANAGEMENT("MM"),
    USER_MANAGEMENT("UM"),
    ROLE_MANAGEMENT("RM"),
    DPX_IMPORT("DI"),
    USER("U"),
    BRANCH("BM"),
    ROLE("R"),
    MODULE_SETUP("MS"),
    ROLE_MODULE_SETUP("RMS"),
    USER_LOG_MODULE("ULM"),
    ACTIVITY_LOG("AL"),
    USER_LOG("UL"),
    OTP_VALIDATION("OV"),
    GET_DIGIPASS_KEY("GDK"),
    DIGIPASS_REQUEST("DR"),
    DIGIPASS_APPROVE("DA"),
    ACTIVATE_DIGIPASS("AD"),
    DEACTIVATE_DIGIPASS("DD"),
    USER_LOG_SETUP("ULS");

    private String abbreviation;

}
