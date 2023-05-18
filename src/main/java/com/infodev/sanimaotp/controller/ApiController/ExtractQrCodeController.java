package com.infodev.sanimaotp.controller.ApiController;

import com.infodev.sanimaotp.dao.DigipassRequestRepository;
import com.infodev.sanimaotp.entities.DigipassRequest;
import com.infodev.sanimaotp.pojo.DigipassRequestPojo;
import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.pojo.IdentifyUserPojo;
import com.infodev.sanimaotp.pojo.OTPResponse;
import com.infodev.sanimaotp.services.apiService.QRExtracter.QRCodeReaderService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;


@RestController
@CrossOrigin
@Api(description = "Get serial key,activation code and QR image URL for activating Digipass mobile application. ")
public class ExtractQrCodeController {

    @Value("${qrCodeFolder}")
    private String qrCodeFilePath;

    @Autowired
    private QRCodeReaderService qrCodeReaderService;

    @Autowired
    private DigipassRequestRepository _dReqRepository;

    @GetMapping(path = "/getActivationCode/{id}")
    public ResponseEntity<GlobalResponse> getActivationCode(@PathVariable final Long id) {

        DigipassRequest digipassRequest = _dReqRepository.findById(id).get();

        final IdentifyUserPojo identifyUserPojo = new IdentifyUserPojo();
        identifyUserPojo.setCentralId(digipassRequest.getCentralId());
        identifyUserPojo.setApplicationId(digipassRequest.getApplicationId());
        identifyUserPojo.setChannelId(digipassRequest.getApplicationType());

        GlobalResponse globalResponse = new GlobalResponse();
        try {
            globalResponse.setStatus(1);
            globalResponse.setData(qrCodeReaderService.getQrImage(identifyUserPojo));
            globalResponse.setMessage("Activation code extracted successfully");
        } catch (Exception e) {
            globalResponse.setMessage(e.getMessage());
            globalResponse.setData(null);
            globalResponse.setStatus(0);
        } finally {
            return new ResponseEntity<>(globalResponse, HttpStatus.OK);
        }
    }

    /*MOBILE OTP GENERATOR*/
    @PostMapping(path = "/api/getActivationCode")
    public ResponseEntity<?> getMobileActivationCode(@RequestBody @Valid IdentifyUserPojo identifyUserPojo) {

        //get digipass
        final DigipassRequest digipassRequest = _dReqRepository
                .getDigipass(identifyUserPojo.getCentralId(), identifyUserPojo.getApplicationId(), identifyUserPojo.getChannelId())
                .orElseThrow(() -> new RuntimeException(
                        String.format("requested digipass with central id ['%s'], application id ['%s'] and application type ['%s'] not found",
                                identifyUserPojo.getCentralId(), identifyUserPojo.getApplicationId(), identifyUserPojo.getChannelId())));

        if (!digipassRequest.getApprovalStatus().equalsIgnoreCase("Approved"))
            throw new RuntimeException(String.format("requested digipass with central id ['%s'], application id ['%s'] and application type ['%s'] not approved",
                    identifyUserPojo.getCentralId(), identifyUserPojo.getApplicationId(), identifyUserPojo.getChannelId()));

        if (digipassRequest.isQrGeneratedStatus())
            throw new RuntimeException(String.format("requested digipass with central id ['%s'], application id ['%s'] and application type ['%s'] has already been activated",
                    identifyUserPojo.getCentralId(), identifyUserPojo.getApplicationId(), identifyUserPojo.getChannelId()));


        try {
            final Map<String, Object> mapData = qrCodeReaderService.getQrImage(identifyUserPojo);

            return new ResponseEntity(GlobalResponse
                    .builder()
                    .data(
                            OTPResponse
                                    .builder()
                                    .url(mapData.get("url").toString())
                                    .serialKey(mapData.get("serialKey").toString())
                                    .activationCode(mapData.get("activationCode").toString())
                                    .status(mapData.get("status").toString())
                                    .applicationId(mapData.get("applicationId").toString())
                                    .build()
                    )
                    .message("Activation code extraction successfully")
                    .status(1)
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(GlobalResponse
                    .builder()
                    .status(0)
                    .data(null)
                    .message("Activation code extraction failure")
                    .build(), HttpStatus.NOT_FOUND);
        }
    }


    @CrossOrigin
    @RequestMapping("api/requestApprove")
    public ResponseEntity<GlobalResponse> digipassApproveRequest(@RequestBody DigipassRequestPojo _dRequestPojo,
                                                                 final Authentication authentication) {
        return new ResponseEntity<>(qrCodeReaderService.digipassApproveRequest(_dRequestPojo, authentication), HttpStatus.OK);
    }


    /*IMAGE DOWNLOAD API*/
    @GetMapping(path = "/download/qr/{serialNumber}")
    public ResponseEntity<Object> downloadFile(@PathVariable final String serialNumber) {
        try {
            final String filePath = qrCodeFilePath + serialNumber + ".jpg";
            final File file = new File(filePath);
            final InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(file));
            final HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
            headers.add("Cache-Control", "no-cache no-store must-revalidate");
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(inputStreamResource);
        } catch (final Exception e) {
            e.printStackTrace();
            return new ResponseEntity("something went wrong.", HttpStatus.BAD_REQUEST);
        }
    }
}
