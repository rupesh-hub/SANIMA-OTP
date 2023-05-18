package com.infodev.sanimaotp.services.apiService.QRExtracter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.infodev.sanimaotp.dao.DigipassRequestRepository;
import com.infodev.sanimaotp.dao.DigipassStatusRepository;
import com.infodev.sanimaotp.dao.UserRepository;
import com.infodev.sanimaotp.entities.DigipassRequest;
import com.infodev.sanimaotp.entities.DigipassStatus;
import com.infodev.sanimaotp.entities.User;
import com.infodev.sanimaotp.enums.DigipassApprovalStatus;
import com.infodev.sanimaotp.pojo.DataTableResponsePojo;
import com.infodev.sanimaotp.pojo.DigipassRequestPojo;
import com.infodev.sanimaotp.pojo.GlobalResponse;
import com.infodev.sanimaotp.pojo.IdentifyUserPojo;
import com.infodev.sanimaotp.services.utils.SetGetAppIdInStatus;
import com.infodev.sanimaotp.services.utils.convertors.DigipassReqResConvertor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QRCodeReaderServiceImpl implements QRCodeReaderService {

    @Value("${qrCodeFolder}")
    private String qrCodeFilePath;

    @Value("${applicationUrl}")
    private String appUrl;

    private final DigipassRequestRepository _dRequestRepos;
    private final DigipassStatusRepository digipassStatusRepository;
    private final UserRepository userRepository;


    public String decodeQRCode(File qrCodeimage) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(qrCodeimage);
        LuminanceSource source = new BufferedImageLuminanceSource(bufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

        try {
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
            System.out.println("There is no QR code in the image");
            return null;
        }
    }

    @Override
    public Map<String, Object> getQrImage(@Valid IdentifyUserPojo identifyUserPojo) throws IOException {
        String decodedText = null;
        String pathName = null;
        String serialNumber = null;

        Map<String, Object> map = new LinkedHashMap<>();
        try {

            DigipassStatus digipassStatus = digipassStatusRepository.getStatusRowByCentralId(identifyUserPojo.getCentralId());
            if (digipassStatus == null) {
                digipassStatus = digipassStatusRepository.getFreeSerialKey();
                if (digipassStatus == null) {
                    throw new IOException("New / Free serial key not available !!");
                }
                digipassStatus.setStatus("P");
                digipassStatus.setCentralId(identifyUserPojo.getCentralId());
                digipassStatusRepository.save(new SetGetAppIdInStatus(identifyUserPojo.getChannelId(), identifyUserPojo.getApplicationId()).setAppId(digipassStatus));
            }

            serialNumber = digipassStatus.getSerialNumber();
            pathName = qrCodeFilePath + serialNumber + ".jpg";
            File file = new File(pathName);
            decodedText = decodeQRCode(file);
            if (decodedText == null) {
                throw new IOException("No QR Code found in the image / No data extracted !!");
            } else {
                XmlMapper xmlMapper = new XmlMapper();
                JsonNode node = xmlMapper.readTree(decodedText.getBytes());
                String appValue = new SetGetAppIdInStatus(identifyUserPojo.getChannelId(), identifyUserPojo.getApplicationId()).getAppId(digipassStatus);
                String sk = String.valueOf(node.get("SN"));
                String ac = String.valueOf(node.get("AC"));
//                map.put("url", qrCodeFilePath + serialNumber + ".jpg");
                map.put("url", String.format("%s/download/qr/%s", appUrl, serialNumber));
                map.put("serialKey", sk.replaceAll("^\"|\"$", ""));
                map.put("activationCode", ac.replaceAll("^\"|\"$", ""));
                map.put("status", digipassStatus.getStatus());
                map.put("applicationId", appValue);
            }
        } catch (Exception e) {
            final String errorClassName = e.getClass().getName().substring(e.getClass().getName().lastIndexOf(".") + 1);
            if (e instanceof DataIntegrityViolationException) {
                throw new IOException(errorClassName + " : Application Id " + identifyUserPojo.getApplicationId() + " is duplicate for entered channel : " + identifyUserPojo.getChannelId());
            } else {
                throw new IOException("Could not get activation code :" + errorClassName + " " + e.getMessage());
            }
        }
        return map;
    }

    @Transactional
    @Override
    public GlobalResponse digipassApproveRequest(final DigipassRequestPojo _dRequestPojo,
                                                 final Authentication authentication) {

        DigipassRequest _dRequest = DigipassReqResConvertor.toDigipassRequest(_dRequestPojo);

        _dRequest.setBranchId(getUserBranchId(authentication.getName()));
        _dRequest.setQrGeneratedStatus(false);

        //TODO: check is data already present - central id and application id
        final Optional<DigipassRequest> digipassRequest = _dRequestRepos.findByCentralIdAndApplicationId(_dRequestPojo.getCentralId(),
                _dRequestPojo.getApplicationId());
        if (digipassRequest.isPresent() && !digipassRequest.get().getApprovalStatus().equalsIgnoreCase(DigipassApprovalStatus.DEACTIVATED.getStatus()))
            throw new RuntimeException("digipass is already present.");

        _dRequestRepos.save(_dRequest);

        return GlobalResponse
                .builder()
                .data(_dRequestPojo)
                .message("data created/updated success.")
                .status(HttpStatus.CREATED.value())
                .build();
    }

    @Override
    @Transactional
    public GlobalResponse digipassRequestAndApprove(DigipassRequestPojo _dRequestPojo, Authentication authentication) {

        final DigipassRequest _dRequest = DigipassReqResConvertor.toDigipassRequest(_dRequestPojo);

        _dRequest.setBranchId(getUserBranchId(authentication.getName()));
        _dRequest.setQrGeneratedStatus(false);

        //TODO: check is data already present - central id and application id
        final Optional<DigipassRequest> digipassRequest = _dRequestRepos.findByCentralIdAndApplicationId(_dRequestPojo.getCentralId(),
                _dRequestPojo.getApplicationId());
        if (digipassRequest.isPresent() && !digipassRequest.get().getApprovalStatus().equalsIgnoreCase(DigipassApprovalStatus.DEACTIVATED.getStatus()))
            throw new RuntimeException("digipass is already present.");

        //TODO: approve request
        _dRequest.setApprovalStatus(DigipassApprovalStatus.APPROVED.getStatus());

        _dRequestRepos.save(_dRequest);

        return GlobalResponse
                .builder()
                .data(_dRequestPojo)
                .message("data created/updated success.")
                .status(HttpStatus.CREATED.value())
                .build();
    }

    @Override
    public List<DigipassRequestPojo> digipassPendingList(final Authentication authentication) {
        final String username = authentication.getName();
        return digipassPendingList(username, getUserBranchId(username));
    }

    @Override
    public List<DigipassRequestPojo> findAllDigipass(final Authentication authentication) {
        final String username = authentication.getName();
        return getDigipassList(username, getUserBranchId(username));
    }
    private List<DigipassRequestPojo> getDigipassList(final String username, final Long branchId) {
        return Optional.ofNullable(username.equalsIgnoreCase("admin") ? _dRequestRepos.findAll() : _dRequestRepos.findAll(branchId)).map(DigipassReqResConvertor::toDigipassReqPojoList).orElse(null);
    }

    @Override
    public DataTableResponsePojo findAllDigipass2(Authentication authentication, int offset, int limit, String searchKey, int draw) {
        final String username = authentication.getName();
        Long branchId = getUserBranchId(username);
        int page = offset/limit;
        Page<DigipassRequest> digipassRequestPage =null;
        if (username.equalsIgnoreCase("admin")){
            digipassRequestPage = _dRequestRepos.findAllForAdmin(PageRequest.of(page, limit));
        }else {
            digipassRequestPage = _dRequestRepos.findAllForBranch(branchId, PageRequest.of(page, limit));
        }
        DataTableResponsePojo dataTableResponsePojo = DataTableResponsePojo.builder()
                .data(digipassRequestPage.getContent())
                .draw(new Integer(draw).toString())
                .recordsTotal(new Long(digipassRequestPage.getTotalElements()).toString())
                .recordsFiltered(new Long(digipassRequestPage.getTotalElements()).toString())
                .pages(new Integer(digipassRequestPage.getTotalPages()).toString())
                .build();
        return dataTableResponsePojo;
    }

    @Override
    public List<DigipassRequestPojo> findAllByApprovedStatus() {
        return Optional.ofNullable(_dRequestRepos.findAllByApprovedStatus()).map(DigipassReqResConvertor::toDigipassReqPojoList).orElse(null);
    }

    @Override
    public DigipassRequestPojo getById(final Long id) {
        return _dRequestRepos.findById(id).map(DigipassReqResConvertor::toDigipassRequestPojo).orElseThrow(() -> new RuntimeException("no record found with id." + id));
    }

    @Override
    public void deleteDigipassRequest(final Long id) {
        _dRequestRepos.deleteById(id);
    }

    @Override
    @Transactional
    public void rejectDigipassRequest(final Long id, final String remarks) {
        final DigipassRequest digipassRequest = fetchDigipassRequest(id);
        digipassRequest.setApprovalStatus(DigipassApprovalStatus.REJECTED.getStatus());
        digipassRequest.setRemarks(remarks);
        _dRequestRepos.save(digipassRequest);
    }

    @Override
    @Transactional
    public void approveDigipassRequest(final Long id) {
        final DigipassRequest digipassRequest = fetchDigipassRequest(id);
        digipassRequest.setApprovalStatus(DigipassApprovalStatus.APPROVED.getStatus());
        _dRequestRepos.save(digipassRequest);
    }

    private DigipassRequest fetchDigipassRequest(final Long id) {
        return _dRequestRepos.findById(id).orElseThrow(() -> new RuntimeException("no record found with id." + id));
    }

    private Long getUserBranchId(final String username) {
        final User currentLoggedUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException(String.format("user with given username '%s' not found.", username)));
        return currentLoggedUser.getBranch().getId();
    }

    private List<DigipassRequestPojo> digipassPendingList(final String username, final Long branchId) {
        return Optional.ofNullable(username.equalsIgnoreCase("admin") ? _dRequestRepos.digipassPendingList() : _dRequestRepos.allDigiRequest(branchId)).map(DigipassReqResConvertor::toDigipassReqPojoList).orElse(null);
    }

}