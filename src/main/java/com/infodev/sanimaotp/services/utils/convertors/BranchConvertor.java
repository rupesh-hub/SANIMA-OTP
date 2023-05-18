package com.infodev.sanimaotp.services.utils.convertors;

import com.infodev.sanimaotp.entities.Branch;
import com.infodev.sanimaotp.pojo.BranchDto;

public class BranchConvertor {

    public static Branch toEntity(BranchDto branchDto) {
        Branch branch = new Branch();
        branch.setName(branchDto.getName());
        branch.setAddress(branchDto.getAddress());
        branch.setStatus(branchDto.getStatus());
        return branch;
    }

    public static BranchDto toDto(Branch branch) {
        BranchDto branchDto = new BranchDto();
        branchDto.setId(branch.getId());
        branchDto.setName(branch.getName());
        branchDto.setAddress(branch.getAddress());
        branchDto.setStatus(branch.getStatus());
        return branchDto;
    }

}
