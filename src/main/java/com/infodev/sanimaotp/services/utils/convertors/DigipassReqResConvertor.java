package com.infodev.sanimaotp.services.utils.convertors;

import com.infodev.sanimaotp.entities.DigipassRequest;
import com.infodev.sanimaotp.pojo.DigipassRequestPojo;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class DigipassReqResConvertor {

    public DigipassRequest toDigipassRequest(DigipassRequestPojo _dRequestPojo){
       return DigipassRequest
                .builder()
               .id(_dRequestPojo.getId())
                .applicationId(_dRequestPojo.getApplicationId())
                .approvalStatus(_dRequestPojo.getApprovalStatus())
                .applicationType(_dRequestPojo.getApplicationType())
                .remarks(_dRequestPojo.getRemarks())
                .status(_dRequestPojo.getStatus())
                .centralId(_dRequestPojo.getCentralId())
               .status(_dRequestPojo.getStatus())
               .toBeApprovedBy(_dRequestPojo.getToBeApprovedBy())
                .build();
    }

    public DigipassRequestPojo toDigipassRequestPojo(DigipassRequest _dRequest){
        return DigipassRequestPojo
                .builder()
                .id(_dRequest.getId())
                .applicationId(_dRequest.getApplicationId())
                .approvalStatus(_dRequest.getApprovalStatus())
                .applicationType(_dRequest.getApplicationType())
                .remarks(_dRequest.getRemarks())
                .status(_dRequest.getStatus())
                .centralId(_dRequest.getCentralId())
                .status(_dRequest.getStatus())
                .toBeApprovedBy(_dRequest.getToBeApprovedBy())
                .build();
    }

    public List<DigipassRequestPojo> toDigipassReqPojoList(List<DigipassRequest> digipassRequestList){
        return digipassRequestList
                .stream()
                .map(digipass -> toDigipassRequestPojo(digipass))
                .collect(Collectors.toList());
    }
 }
