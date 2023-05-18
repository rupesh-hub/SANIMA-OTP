package com.infodev.sanimaotp.services.adminService.resetDigipass;

import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.pojo.IdentifyUserPojo;

public interface ResetDigipassService {
    public GlobalResponse resetToken(IdentifyUserPojo identifyUserPojo) throws Exception;
}
