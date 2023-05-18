package com.infodev.sanimaotp.pojo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class GenerateChallengePojo {
    @NotNull
    @Size(min = 7, max = 17)
    String channelId;
    @NotNull
    @Size(min = 4, max = 200)
    String centralId;
    @NotNull
    @Size(min = 4, max = 200)
    String applicationId;

    public GenerateChallengePojo() {
    }

    public GenerateChallengePojo(String channelId, String centralId, String applicationId) {

        this.channelId = channelId;
        this.centralId = centralId;
        this.applicationId = applicationId;
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
