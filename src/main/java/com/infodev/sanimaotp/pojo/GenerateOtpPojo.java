package com.infodev.sanimaotp.pojo;

public class GenerateOtpPojo{
    private String centralId;
    private String applicationId;
    private String channelId;
    private String challenge;

    public GenerateOtpPojo(String centralId, String applicationId, String channelId, String challenge) {
        this.centralId = centralId;
        this.applicationId = applicationId;
        this.channelId = channelId;
        this.challenge = challenge;
    }

    public GenerateOtpPojo() {
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

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }
}
