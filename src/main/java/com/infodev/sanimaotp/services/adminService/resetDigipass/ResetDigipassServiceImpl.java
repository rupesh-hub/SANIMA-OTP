package com.infodev.sanimaotp.services.adminService.resetDigipass;

import com.infodev.sanimaotp.dao.DigipassDetailsBackupRepository;
import com.infodev.sanimaotp.dao.DigipassDetailsRepository;
import com.infodev.sanimaotp.dao.DigipassStatusRepository;
import com.infodev.sanimaotp.entities.DigipassDetail;
import com.infodev.sanimaotp.entities.DigipassStatus;
import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.pojo.IdentifyUserPojo;
import com.infodev.sanimaotp.services.apiService.QRExtracter.QRCodeReaderService;
import com.vasco.utils.AAL2Wrap;
import com.vasco.utils.KernelParms;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ResetDigipassServiceImpl implements ResetDigipassService {

    @Autowired
    private DigipassStatusRepository digipassStatusRepository;

    @Autowired
    private DigipassDetailsBackupRepository digipassDetailsBackupRepository;

    @Autowired
    private DigipassDetailsRepository digipassDetailsRepository;

    @Autowired
    private QRCodeReaderService qrCodeReaderService;


    @Override
    public GlobalResponse resetToken(IdentifyUserPojo identifyUserPojo) throws Exception {
        DigipassStatus digipassStatus = digipassStatusRepository.getStatusRowByCentralId(identifyUserPojo.getCentralId());
        if (digipassStatus == null) {
            throw new Exception("Central Id not found for " + identifyUserPojo.getCentralId());
        }
        digipassStatus.setStatus("P");
        digipassStatusRepository.save(digipassStatus);
        String blobId = digipassStatus.getSerialNumber() + identifyUserPojo.getChannelId();


        List<DigipassDetail> digipassDetails = digipassDetailsRepository.getAllBlobsBySerialKey(digipassStatus.getSerialNumber());
        AAL2Wrap aal2Wrap = new AAL2Wrap();

        Map<String, Object> activationData = qrCodeReaderService.getQrImage(identifyUserPojo);
        String activationCode = (String) activationData.get("activationCode");

        byte[][] dPData_Table = new byte[digipassDetails.size()][248];
        short appli_Count = 8;
        KernelParms kParms = new KernelParms();
        String staticVectorIn = null;
        String sharedData = null;
        String alea = null;
        int activationFlags = 0x00000001;
        byte[] serialNumberSuffix = digipassStatus.getSerialNumber().substring(3).getBytes();
        byte[] xFAD = activationCode.getBytes();
        byte[] xERC = null;


        for (int i = 0; i < digipassDetails.size(); i++) {
            dPData_Table[i] = digipassDetails.get(i).getAppBlob().getBytes();
        }

        System.out.println(dPData_Table);


        int resetStat = aal2Wrap.AAL2GenActivationCodeXErc(
                dPData_Table,
                appli_Count,
                kParms,
                staticVectorIn,
                sharedData,
                alea,
                activationFlags,
                serialNumberSuffix,
                xFAD,
                xERC);
        System.out.println(resetStat);
        if(resetStat==0){
            System.out.printf("Reset success");
        }

        return new GlobalResponse(0,"failed",null);
    }
}
