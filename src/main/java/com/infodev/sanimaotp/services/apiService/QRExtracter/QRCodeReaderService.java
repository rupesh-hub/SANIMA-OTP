package com.infodev.sanimaotp.services.apiService.QRExtracter;

import com.infodev.sanimaotp.pojo.DataTableResponsePojo;
import com.infodev.sanimaotp.pojo.DigipassRequestPojo;
import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.pojo.IdentifyUserPojo;
import org.springframework.security.core.Authentication;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface QRCodeReaderService {  //2586
     String decodeQRCode(File qrImage) throws IOException;
     Map<String,Object> getQrImage(IdentifyUserPojo identifyUserPojo) throws IOException;
    GlobalResponse digipassApproveRequest(final DigipassRequestPojo _dRequestPojo, Authentication authentication);
    GlobalResponse digipassRequestAndApprove(final DigipassRequestPojo _dRequestPojo, Authentication authentication);

    List<DigipassRequestPojo> digipassPendingList(final Authentication authentication);
    List<DigipassRequestPojo> findAllDigipass(final Authentication authentication);
    DataTableResponsePojo findAllDigipass2(Authentication authentication, int offset, int limit, String searchKey, int draw);
    List<DigipassRequestPojo> findAllByApprovedStatus();
    DigipassRequestPojo getById(final Long id);
    void deleteDigipassRequest(final Long id);
    void rejectDigipassRequest(final Long id, final String remarks);
    void approveDigipassRequest(final Long id);

}
