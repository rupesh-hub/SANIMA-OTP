package com.infodev.sanimaotp.services.adminService.dpximport;

import com.infodev.sanimaotp.dao.DigipassBatchRepository;
import com.infodev.sanimaotp.dao.DigipassDetailsBackupRepository;
import com.infodev.sanimaotp.dao.DigipassDetailsRepository;
import com.infodev.sanimaotp.dao.DigipassStatusRepository;
import com.infodev.sanimaotp.entities.DigipassBatch;
import com.infodev.sanimaotp.entities.DigipassDetail;
import com.infodev.sanimaotp.entities.DigipassDetailBackup;
import com.infodev.sanimaotp.entities.DigipassStatus;
import com.infodev.sanimaotp.exception.DpxImportException;
import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.services.utils.StorageService;
import com.vasco.utils.AAL2Wrap;
import com.vasco.utils.KernelParms;
import com.vasco.utils.response.RespDPXGetTokenBlobsEx;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class ImportDpxServiceImpl implements ImportDpxService {

    @Autowired
    private StorageService storageService;

    @Autowired
    private DigipassStatusRepository digipassStatusRepository;

    @Autowired
    private DigipassBatchRepository digipassBatchRepository;
    @Autowired
    private DigipassDetailsBackupRepository digipassDetailsBackupRepository;

    @Autowired
    private DigipassDetailsRepository digipassDetailsRepository;

    @Override
    @Transactional
    public GlobalResponse importDpxService(MultipartFile dpxFile, String InitKey) throws Exception {

        GlobalResponse globalResponse = new GlobalResponse();
        AAL2Wrap vcWrap = null;
        try {

            globalResponse.setMessage("Keys successfully imported into database.");
            globalResponse.setStatus(1);
            globalResponse.setData("success");
            vcWrap = new AAL2Wrap();
            log.info("Copying dpx file");
            storageService.storeDPXFile(dpxFile.getOriginalFilename(), dpxFile);
            log.info("Copy Completed");
            log.info("Loading dpx file");
            Resource storedFile = storageService.loadFile(dpxFile.getOriginalFilename());
            log.info("Load completed");
            File mf = storedFile.getFile();

            String[] tokenList = vcWrap.AAL2DPXInit(mf.getAbsolutePath(), InitKey);

            KernelParms kernelParms = new KernelParms();
            RespDPXGetTokenBlobsEx res;
            int tokenCount = 0;
            String firstSerialKey = null;


            AAL2Wrap batchVc = new AAL2Wrap();
            batchVc.AAL2DPXInit(mf.getAbsolutePath(), InitKey);

            res = batchVc.AAL2DPXGetTokenBlobsEx(kernelParms);
            firstSerialKey = res.getSerial();

            batchVc.AAL2DPXClose();
            String batchId = firstSerialKey;

            DigipassBatch batch = new DigipassBatch();

            Optional<DigipassBatch> checkIfBatchExists = digipassBatchRepository.findById(batchId);
            System.out.println(checkIfBatchExists);
            if (checkIfBatchExists.isPresent()) {
                throw new Exception("Digipass keys already imported from this dpx file");
            }

            batch.setBatchId(batchId);
            batch.setImportedBy("admin5");
            batch.setImportDate(new Date().toString());

            digipassBatchRepository.save(batch);

            log.info("Starting Save to Database");
            do {
                res = vcWrap.AAL2DPXGetTokenBlobsEx(kernelParms);
                if (res != null) {
                    tokenCount++;
                    DigipassStatus stat = new DigipassStatus();
                    stat.setSerialNumber(res.getSerial());
                    stat.setStatus("N");
                    digipassStatusRepository.save(stat);
                    String tokenType = res.getTokenType();
                    String[] serialAppName = res.getSerialAppl();
                    String[] appMode = res.getAuthMode();
                    String[] appBlob = res.getStringDPData();
                    for (int i = 0; i < appBlob.length; i++) {
                        DigipassDetail digipassDetail = new DigipassDetail();
                        digipassDetail.setAppMode(appMode[i]);
                        digipassDetail.setAppType(tokenType);
                        digipassDetail.setSerialAppKey(serialAppName[i]);
                        digipassDetail.setAppBlob(appBlob[i]);
                        digipassDetail.setBatchId(new DigipassBatch(batch.getBatchId()));
                        digipassDetail.setSerialNumber(new DigipassStatus(stat.getSerialNumber()));

                        log.info("Saving for {}",stat.getSerialNumber());
                        DigipassDetail dResponse = digipassDetailsRepository.save(digipassDetail);
                        log.info("Save Success");

                        //saving in backup table
                        DigipassDetailBackup digipassDetailBackup = new DigipassDetailBackup();
                        digipassDetailBackup.setAppMode(appMode[i]);
                        digipassDetailBackup.setAppType(tokenType);
                        digipassDetailBackup.setSerialAppKey(serialAppName[i]);
                        digipassDetailBackup.setAppBlob(appBlob[i]);
                        digipassDetailBackup.setBatchId(new DigipassBatch(batch.getBatchId()));
                        digipassDetailBackup.setSerialNumber(new DigipassStatus(stat.getSerialNumber()));
                        digipassDetailsBackupRepository.save(digipassDetailBackup);

                        if (dResponse == null) {
                            throw new DpxImportException("DPX import failed on " + i + "th dpx key import");
                        } else {


                        }
                    }
                } else {
                    if (tokenCount == 0) {
                        vcWrap.AAL2DPXClose();

                        globalResponse.setMessage("DPX import failed, transport key invalid.");
                        throw new DpxImportException("DPX import failed, transport key invalid.");
                    }
                }

            } while (res != null);
            vcWrap.AAL2DPXClose();
        } catch (Exception e) {
            vcWrap.AAL2DPXClose();
            throw new Exception(e.getMessage());
        } finally {

            storageService.deleteFile(dpxFile);
        }
        return globalResponse;


    }
}
