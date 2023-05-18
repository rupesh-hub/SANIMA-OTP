package com.infodev.sanimaotp.pojo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class IdentifyUserPojo {
    @NotNull
    @Size(min = 7, max = 17)
    String channelId;
    @NotNull
    @Size(min = 4,max=200)
    String applicationId;
    @NotNull
    @Size(min = 4,max=200)
    String centralId;

    public IdentifyUserPojo(String channelId, String applicationId, String centralId) {
        this.channelId = channelId;
        this.applicationId = applicationId;
        this.centralId = centralId;
    }

    public IdentifyUserPojo() {
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getCentralId() {
        return centralId;
    }

    public void setCentralId(String centralId) {
        this.centralId = centralId;
    }
}
