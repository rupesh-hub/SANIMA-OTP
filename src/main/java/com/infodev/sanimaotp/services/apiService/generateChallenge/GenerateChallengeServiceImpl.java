package com.infodev.sanimaotp.services.apiService.generateChallenge;

import com.infodev.sanimaotp.dao.DigipassBatchRepository;
import com.infodev.sanimaotp.dao.DigipassDetailsRepository;
import com.infodev.sanimaotp.dao.DigipassStatusRepository;
import com.infodev.sanimaotp.entities.DigipassDetail;
import com.infodev.sanimaotp.entities.DigipassStatus;
import com.infodev.sanimaotp.pojo.GenerateChallengePojo;
import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.services.utils.SetGetAppIdInStatus;
import com.vasco.utils.AAL2Wrap;
import com.vasco.utils.KernelParms;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("impl1")
public class GenerateChallengeServiceImpl implements GenerateChallengeService {

//    public final String ANSI_RESET = "\u001B[0m";
//    public final String ANSI_RED = "\u001B[31m";
//    public final String ANSI_GREEN = "\u001B[32m";
//    public final String ANSI_BLUE = "\u001B[34m";
//    public final String ANSI_PURPLE = "\u001B[35m";

    @Autowired
    private DigipassDetailsRepository digipassDetailsRepository;

    @Autowired
    private DigipassBatchRepository digipassBatchRepository;

    @Autowired
    private DigipassStatusRepository digipassStatusRepository;


    @Override
    public GlobalResponse generateChallenge(GenerateChallengePojo generateChallengePojo) throws Exception {
        GlobalResponse globalResponse = new GlobalResponse();
        AAL2Wrap vcWrap = new AAL2Wrap();
          /*
            *ValidateRequestParameters.validateRequest() : Validation of request parameters values is done by this method
            * */


            /*
            * Fetches Digipass status based on central customer Id
            * */


        DigipassStatus digipassStatus = digipassStatusRepository.getStatusRowByCentralId(generateChallengePojo.getCentralId());

        if (digipassStatus == null) {
            throw new Exception("User not activated");
        } //throw exception if customer Id not found


        if (digipassStatus.getStatus().equals("A")) { // if digipass status is in active state

            /**
             *Gets application Id -> fetches application Id on status row
             */

            String blobId = digipassStatus.getSerialNumber() + generateChallengePojo.getChannelId(); //gets blob primary key -> example : serial key + channel id = FDO2212259APP5_IBANK)

            DigipassDetail digipassData = digipassDetailsRepository.getDataBySerialAppKey(blobId); // Fetch respective blob row based on blobId


            KernelParms kParam = new KernelParms();
            kParam.setParm(4, 3);
            //System.out.println();
            //System.out.println(ANSI_GREEN + "Before calling AAL2GenerateChallenge" + ANSI_RESET);
            //System.out.println();


            //System.out.println(ANSI_BLUE + "Channel :" + generateChallengePojo.getChannelId());
            //System.out.println("Application ID :" + generateChallengePojo.getApplicationId());
            //System.out.println("Central Id: " + generateChallengePojo.getCentralId());

            //System.out.println("Blob Id: " + digipassData.getSerialAppKey());
            //System.out.println("BLOB :" + digipassData.getAppBlob() + ANSI_RESET);


            byte[] appBlob = digipassData.getAppBlob().getBytes(); // blob is stored as text in database, which is converted to byte array for calling Generate Challenge api


//            String appTypeBefore = vcWrap.AAL2GetTokenSingleInfo(appBlob, kParam, 1);
//            String useCountBefore = vcWrap.AAL2GetTokenSingleInfo(appBlob, kParam, 2);
//            String lastTimeUsageCountBefore = vcWrap.AAL2GetTokenSingleInfo(appBlob, kParam, 3);
//            String timeShift = vcWrap.AAL2GetTokenSingleInfo(appBlob, kParam, 4);

            //System.out.println(ANSI_PURPLE + "AppType :" + appTypeBefore + " Use count : " + ANSI_RED + useCountBefore + ANSI_PURPLE + " Last time usage :" + ANSI_RED + lastTimeUsageCountBefore + ANSI_RESET);
            //System.out.println("Time shift :" + timeShift);


            int[] kparams = kParam.getParms();
            String[] kparamName = {
                    "ITimeWindow",
                    "STimeWindow",
                    "DiagLevel",
                    "GMTAdjust",
                    "CheckChallenge",
                    "IThreshold",
                    "SThreshold",
                    "ChkInactDays",
                    "DeriveVector",
                    "SyncWindow",
                    "OnLineSG",
                    "EventWindow",
                    "HSMSlotID",
                    "StorageKeyID",
                    "TransportKeyID",
                    "StorageDeriveKey1",
                    "StorageDeriveKey2",
                    "StorageDeriveKey3",
                    "StorageDeriveKey4"
            };
            for (int i = 0; i < kparams.length; i++) { //63772028  //transportKey : 8388607
                System.out.println("Index " + i + " : " + kparamName[i] + " : " + kparams[i]);
            }
//

            String challenge = vcWrap.AAL2GenerateChallenge(appBlob, kParam);


            if (digipassData.getAppBlob().equals(new String(appBlob))) { //if API call doesn't updates app blob after challenge generation, exception is thrown

                throw new Exception("No new app blob generated from AAL2GenerateChallenge API call");

            } else {
                digipassData.setAppBlob(new String(appBlob)); // set updated app blob on field of DigipassDetail class
                digipassDetailsRepository.save(digipassData);
                //System.out.println();
                //System.out.println(ANSI_GREEN + "After calling AAL2GenerateChallenge" + ANSI_RESET);
                //System.out.println();
                KernelParms kParam2 = new KernelParms();


                //System.out.println(ANSI_BLUE + "Blob Id :" + digipassData.getSerialAppKey());
                //System.out.println("BLOB " + digipassData.getAppBlob());


                byte[] nextBlob = digipassDetailsRepository.getDataBySerialAppKey(blobId).getAppBlob().getBytes();

//                String appTypeAfter = vcWrap.AAL2GetTokenSingleInfo(nextBlob, kParam2, 1);
//                String useCountAfter = vcWrap.AAL2GetTokenSingleInfo(nextBlob, kParam2, 2);
//                String lastTimeUsageCountAfter = vcWrap.AAL2GetTokenSingleInfo(nextBlob, kParam2, 3);
//                String timeShiftAfter = vcWrap.AAL2GetTokenSingleInfo(appBlob, kParam, 4);

                //System.out.println("Ret Code :" + vcWrap.getRetCode());
                //System.out.println("Last error :" + vcWrap.getLastError() + ANSI_RESET);


                //System.out.println(ANSI_PURPLE + "AppType :" + ANSI_RED + appTypeAfter + ANSI_PURPLE + " Use count : " + ANSI_RED + useCountAfter + ANSI_RESET + " Last time usage :" + ANSI_RED + lastTimeUsageCountAfter + ANSI_RESET);
                //System.out.println("timeShift :" + timeShiftAfter);
                String appId = new SetGetAppIdInStatus(generateChallengePojo.getChannelId(), generateChallengePojo.getApplicationId()).getAppId(digipassStatus);

                if (appId == null) { //if challenge is generated and no application id is found, insert application id on respective columns based on app being used
                    digipassStatus = new SetGetAppIdInStatus(generateChallengePojo.getChannelId(), generateChallengePojo.getApplicationId()).setAppId(digipassStatus);
                    digipassStatusRepository.save(digipassStatus);
                }  //#log_statement = 'all'			# none, ddl, mod, all


                globalResponse.setMessage("New challenge " + challenge + " generated successfully");
                //System.out.println(ANSI_GREEN + "New challenge " + ANSI_BLUE + challenge + ANSI_GREEN + " generated successfully" + ANSI_RESET);
                globalResponse.setStatus(1);
                globalResponse.setData(challenge);
            }
        } else {
            throw new Exception("User is not activated!!");
        }
        return globalResponse;
    }
}
