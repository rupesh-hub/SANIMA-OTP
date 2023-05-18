package com.infodev.sanimaotp.services.apiService.activateDigipass;

import com.infodev.sanimaotp.entities.DigipassStatus;
import com.infodev.sanimaotp.pojo.ActivateDigipassPojo;
import com.infodev.sanimaotp.pojo.GlobalResponse;

public interface ActivateDigipassService {
    public GlobalResponse enterDerivationKey(ActivateDigipassPojo activateDigipassPojo) throws Exception;
    public void updateDigipassStatus(ActivateDigipassPojo activateDigipassPojo, DigipassStatus digipassStatus) throws  Exception;
}
