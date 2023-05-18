package com.infodev.sanimaotp.services.adminService.deactivateDigipass;

import com.infodev.sanimaotp.dao.DigipassDeactivationHistoryRepository;
import com.infodev.sanimaotp.dao.DigipassRequestRepository;
import com.infodev.sanimaotp.dao.DigipassStatusRepository;
import com.infodev.sanimaotp.entities.DigipassDeactivationHistory;
import com.infodev.sanimaotp.entities.DigipassRequest;
import com.infodev.sanimaotp.entities.DigipassStatus;
import com.infodev.sanimaotp.enums.DigipassApprovalStatus;
import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.pojo.IdentifyUserPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class DeactivateDigipassServiceImpl implements DeactivateDigipassService {

    private final DigipassStatusRepository digipassStatusRepository;
    private final DigipassDeactivationHistoryRepository digipassDeactivationHistoryRepository;
    private final DigipassRequestRepository _dpRepository;

    @Autowired
    public DeactivateDigipassServiceImpl(DigipassStatusRepository digipassStatusRepository,
                                         DigipassDeactivationHistoryRepository digipassDeactivationHistoryRepository,
                                         DigipassRequestRepository dpRepository) {
        this.digipassStatusRepository = digipassStatusRepository;
        this.digipassDeactivationHistoryRepository = digipassDeactivationHistoryRepository;
        this._dpRepository = dpRepository;
    }

    @Override
    public GlobalResponse deactivateDigipass(IdentifyUserPojo identifyUserPojo) {
        final String centralId = identifyUserPojo.getCentralId();
        try {
            if (null == identifyUserPojo.getCentralId()) {
                throw new RuntimeException("Central Id cannot be empty");
            }
            final DigipassStatus digipassStatus =
                    Optional.ofNullable(digipassStatusRepository.getStatusRowByCentralId(centralId))
                            .orElseThrow(() ->
                                    new RuntimeException(String.format("Central Id not found for ", centralId)));

            final DigipassRequest digipassRequest = _dpRepository.findByCentralId(centralId)
                    .orElseThrow(() -> new RuntimeException(String.format("Central Id not found for ", centralId)));
            digipassRequest.setApprovalStatus(DigipassApprovalStatus.DEACTIVATED.getStatus());
            digipassRequest.setApplicationId(digipassRequest.getApplicationId().concat("-").concat(DigipassApprovalStatus.DEACTIVATED.getStatus()));
            digipassRequest.setCentralId(digipassRequest.getCentralId().concat("-").concat(DigipassApprovalStatus.DEACTIVATED.getStatus()));
            final DigipassDeactivationHistory digipassDeactivationHistory = new DigipassDeactivationHistory(digipassStatus);

            digipassStatus.setStatus("N");
            digipassStatus.setCentralId(null);
            digipassStatus.setApp1Mb(null);
            digipassStatus.setApp2Kiosk(null);
            digipassStatus.setApp3Atm(null);
            digipassStatus.setApp4Vbv(null);
            digipassStatus.setApp5Ibank(null);
            digipassStatus.setApp6VbvCr(null);
            digipassStatus.setApp7Kioskcr(null);
            digipassStatus.setApp8FtSig(null);
            digipassStatusRepository.save(digipassStatus);
            digipassDeactivationHistoryRepository.save(digipassDeactivationHistory);
            _dpRepository.save(digipassRequest);


            GlobalResponse globalResponse = new GlobalResponse(1, null, "Deactivation Completed");
            return globalResponse;
        } catch (Exception e) {
            e.printStackTrace();
            GlobalResponse globalResponse = new GlobalResponse(0, null, e.getMessage());
            return globalResponse;
        }
    }
}
