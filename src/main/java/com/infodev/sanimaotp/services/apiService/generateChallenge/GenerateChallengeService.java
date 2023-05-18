package com.infodev.sanimaotp.services.apiService.generateChallenge;

import com.infodev.sanimaotp.pojo.GenerateChallengePojo;
import com.infodev.sanimaotp.pojo.GlobalResponse;

public interface GenerateChallengeService {
    public GlobalResponse generateChallenge(GenerateChallengePojo generateChallengePojo) throws  Exception;
}
