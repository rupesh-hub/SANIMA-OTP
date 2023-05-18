package com.infodev.sanimaotp.services.adminService.branch;

import com.infodev.sanimaotp.pojo.BranchDto;

import java.util.List;

public interface IBranchService {

    BranchDto saveBranch(BranchDto branchDto);

    BranchDto getBranchByName(String branchName);

    BranchDto getBranchById(Long branchId);

    List<BranchDto> allBranches();

    List<BranchDto> allActiveBranches();

    BranchDto updateBranch(BranchDto branchDto);

    boolean updateStatus(Long branchId);

}
