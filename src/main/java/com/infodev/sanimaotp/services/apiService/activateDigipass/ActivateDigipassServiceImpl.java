package com.infodev.sanimaotp.services.apiService.activateDigipass;

import com.infodev.sanimaotp.dao.DigipassDetailsRepository;
import com.infodev.sanimaotp.dao.DigipassRequestRepository;
import com.infodev.sanimaotp.dao.DigipassStatusRepository;
import com.infodev.sanimaotp.entities.DigipassDetail;
import com.infodev.sanimaotp.entities.DigipassRequest;
import com.infodev.sanimaotp.entities.DigipassStatus;
import com.infodev.sanimaotp.pojo.ActivateDigipassPojo;
import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.services.utils.SetGetAppIdInStatus;
import com.vasco.utils.AAL2Wrap;
import com.vasco.utils.KernelParms;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@Slf4j
public class ActivateDigipassServiceImpl implements ActivateDigipassService {



    @Autowired
    private DigipassDetailsRepository digipassDetailsRepository;

    @Autowired
    private DigipassStatusRepository digipassStatusRepository;

    @Autowired
    private DigipassRequestRepository _dRequestRepos;

    @Override
    @Transactional(rollbackOn = {Exception.class})
    public GlobalResponse enterDerivationKey(ActivateDigipassPojo activateDigipassPojo) throws Exception {
        AAL2Wrap aal2Wrap = new AAL2Wrap();
        GlobalResponse globalResponse = new GlobalResponse();
        DigipassStatus digipassStatus = new DigipassStatus();
        List<DigipassDetail> digipassDetails = new ArrayList<>();
        try {
            digipassStatus = digipassStatusRepository.getSerialRowByStatus(activateDigipassPojo.getCentralId(),"P");
            if(digipassStatus==null){
                throw new Exception("No pending activation found for entered central Id");
            }
            String appID= new SetGetAppIdInStatus(activateDigipassPojo.getChannelId(),activateDigipassPojo.getApplicationId()).getAppId(digipassStatus);
            if(!appID.equals(activateDigipassPojo.getApplicationId())){
                throw new Exception("Trying to activate application id "+activateDigipassPojo.getApplicationId()+" instead of previously stored id :"+appID);
            }


            digipassDetails = digipassDetailsRepository.getAllBlobsBySerialKey(digipassStatus.getSerialNumber());
        } catch (Exception e) {
            throw new Exception(e.getMessage());

        }

        log.info("Size of digipassDetails {}",digipassDetails.size());
        byte[][] blobList = new byte[digipassDetails.size()][248];

        for (int i = 0; i < digipassDetails.size(); i++) {
            log.info("Adding BLOB of {}",digipassDetails.get(i).getSerialAppKey());
            blobList[i] = digipassDetails.get(i).getAppBlob().getBytes();
        }
        short appCount = 8;

        KernelParms kernelParms = new KernelParms();

        int vcParameter = 0;

        log.info("Activating with Derivation code {} for serial no {} and central id {}",activateDigipassPojo.getDerivationCode(),digipassStatus.getSerialNumber(),activateDigipassPojo.getCentralId());
        int res = aal2Wrap.AAL2DeriveTokenBlobs(blobList, kernelParms, appCount, null, activateDigipassPojo.getDerivationCode(), vcParameter);
        if (res == 0) {
            System.out.println(res);
            System.out.println("Success 0 response received");
            for (int j = 0; j < digipassDetails.size(); j++) {
                DigipassDetail digipassDetail = digipassDetails.get(j);
                String blob = new String(blobList[j]);
                digipassDetail.setAppBlob(blob);
                try {
                    digipassDetailsRepository.save(digipassDetail);
                } catch (Exception e) {
                    throw new Exception("Error saving to Digipass table");
                }
            }

            this.updateDigipassStatus(activateDigipassPojo,digipassStatus);
            globalResponse.setStatus(1);
            List<Map<String,String>> list= new ArrayList();
            Map<String,String> map= new LinkedHashMap<>();
            map.put("serialNumber",digipassStatus.getSerialNumber());
            map.put("centralId",digipassStatus.getCentralId());
            map.put("applicationId",new SetGetAppIdInStatus(activateDigipassPojo.getChannelId(),activateDigipassPojo.getApplicationId()).getAppId(digipassStatus));
            map.put("status",digipassStatus.getStatus());
            list.add(map);
            globalResponse.setData(list);
            globalResponse.setMessage("Success : User "+activateDigipassPojo.getCentralId()+" activated with serial "+digipassStatus.getSerialNumber());
        } else {
            System.out.println(res);
            System.out.println("Failure no 0 response found");
            throw new Exception("Digipass activation failed ! Error code :"+ res);
        }

        DigipassRequest digipassRequest = this._dRequestRepos.findByCentralId(activateDigipassPojo.getCentralId()).orElseThrow(() -> new RuntimeException("no record found."));
        digipassRequest.setQrGeneratedStatus(true);

        this._dRequestRepos.save(digipassRequest);
        return globalResponse;
    }

    @Override
    public void updateDigipassStatus(ActivateDigipassPojo activateDigipassPojo,DigipassStatus digipassStatus) throws Exception {
        try {
            digipassStatus.setStatus("A");
            digipassStatus.setCentralId(activateDigipassPojo.getCentralId());
            digipassStatusRepository.save(new SetGetAppIdInStatus(activateDigipassPojo.getChannelId(),activateDigipassPojo.getApplicationId()).setAppId(digipassStatus));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Exception("Error updating digipass status: " + e.getMessage());
        }
    }

}


