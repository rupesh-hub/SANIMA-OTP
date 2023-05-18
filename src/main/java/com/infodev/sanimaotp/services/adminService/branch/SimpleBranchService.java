package com.infodev.sanimaotp.services.adminService.branch;

import com.infodev.sanimaotp.dao.BranchRepository;
import com.infodev.sanimaotp.entities.Branch;
import com.infodev.sanimaotp.pojo.BranchDto;
import com.infodev.sanimaotp.services.utils.convertors.BranchConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SimpleBranchService implements IBranchService {

    private final BranchRepository branchRepository;
    private static final String NO_BRANCH_FOUND_BY_ID = "no branch present with id %s.";

    @Autowired
    public SimpleBranchService(BranchRepository branchRepository) {
        this.branchRepository = branchRepository;
    }

    @Override
    public BranchDto saveBranch(final BranchDto branchDto) {
        if (branchDto.getId() == null)
            return Optional.ofNullable(branchRepository.save(BranchConvertor.toEntity(branchDto)))
                    .map(BranchConvertor::toDto)
                    .orElse(null);
        else
            return this.updateBranch(branchDto);
    }

    @Override
    public BranchDto getBranchByName(final String branchName) {
        return null;
    }

    @Override
    public BranchDto getBranchById(final Long branchId) {
        return branchRepository.findById(branchId)
                .map(BranchConvertor::toDto)
                .orElseThrow(() ->
                        new RuntimeException(String.format(NO_BRANCH_FOUND_BY_ID, branchId)));
    }

    @Override
    public List<BranchDto> allBranches() {
        return branchRepository
                .findAll()
                .stream()
                .map(BranchConvertor::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<BranchDto> allActiveBranches() {
        return branchRepository
                .fetchAll()
                .stream()
                .map(BranchConvertor::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BranchDto updateBranch(final BranchDto branchDto) {
        final Long branchId = branchDto.getId();
        final Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() ->
                        new RuntimeException(String.format(NO_BRANCH_FOUND_BY_ID, branchId)));

        branch.setName(branchDto.getName());
        branch.setStatus(branchDto.getStatus());
        branch.setAddress(branchDto.getAddress());

        return Optional.ofNullable(branchRepository.save(branch))
                .map(BranchConvertor::toDto)
                .orElse(null);
    }

    @Override
    public boolean updateStatus(final Long branchId) {
        final Branch branch = branchRepository.findById(branchId)
                .orElseThrow(() ->
                        new RuntimeException(String.format(NO_BRANCH_FOUND_BY_ID, branchId)));
        branch.setStatus(branch.getStatus() ? false : true);

        return (branchRepository.save(branch).getId() == branchId);
    }

}
