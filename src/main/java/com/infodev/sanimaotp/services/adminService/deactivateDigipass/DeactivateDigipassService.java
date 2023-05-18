package com.infodev.sanimaotp.services.adminService.deactivateDigipass;

import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.pojo.IdentifyUserPojo;

public interface DeactivateDigipassService {
    GlobalResponse deactivateDigipass(IdentifyUserPojo identifyUserPojo);
}
