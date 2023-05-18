package com.infodev.sanimaotp.pojo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ActivateDigipassPojo {
    @NotNull
    @Size(min=17,max=18,message = "First OTP / Derivation code must be of 18 digits.")
    private String derivationCode;
    @NotNull
    @Size(min=7,max=17)
    private String channelId;
    @NotNull
    @Size(min=4,max=200)
    private String centralId;
    @NotNull
    @Size(min=4,max=200)
    private String applicationId;

    public ActivateDigipassPojo(String derivationCode, String channelId, String centralId, String applicationId) {
        this.derivationCode = derivationCode;
        this.channelId = channelId;
        this.centralId = centralId;
        this.applicationId = applicationId;
    }

    public ActivateDigipassPojo() {
    }

    public String getDerivationCode() {
        return derivationCode;
    }

    public void setDerivationCode(String derivationCode) {
        this.derivationCode = derivationCode;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getCentralId() {
        return centralId;
    }

    public void setCentralId(String centralId) {
        this.centralId = centralId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }
}
