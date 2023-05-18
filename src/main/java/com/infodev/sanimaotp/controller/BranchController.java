package com.infodev.sanimaotp.controller;

import com.infodev.sanimaotp.controller.base.BaseController;
import com.infodev.sanimaotp.enums.ModuleEnum;
import com.infodev.sanimaotp.pojo.BranchDto;
import com.infodev.sanimaotp.services.adminService.branch.IBranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/branch_")
public class BranchController extends BaseController {

    private final IBranchService branchService;

    @Autowired
    public BranchController(IBranchService branchService) {
        this.branchService = branchService;
        this.module1 = ModuleEnum.BRANCH.getAbbreviation();
    }

    @GetMapping
    @PreAuthorize("hasPermission(#this.this.module1,'')")
    public String branchPage(final Model model) {
        model.addAttribute("branchList", branchService.allBranches());
        return "pages/branch";
    }

    /*SAVE BRANCH*/
    @PostMapping
    public ResponseEntity<BranchDto> saveBranch(@RequestBody final BranchDto branchDto) {
        return ResponseEntity.ok(branchService.saveBranch(branchDto));
    }

    /*GET BRANCH BY NAME*/
    @GetMapping("/name/{name}")
    public ResponseEntity<BranchDto> getBranchByName(@PathVariable("name") final String branchName) {
        return null;
    }

    /*GET BRANCH BY ID*/
    @GetMapping("/id/{id}")
    public ResponseEntity<BranchDto> getBranchById(@PathVariable("id") final Long branchId) {
        return ResponseEntity.ok(branchService.getBranchById(branchId));
    }

    /*GET ALL BRANCHES*/
    @GetMapping("/all")
    public ResponseEntity<?> allBranches() {
        return ResponseEntity.ok(branchService.allBranches());
    }

    /*DELETE BRANCH*/
    @GetMapping("/changeStatus_/{id}")
    public String updateStatus(@PathVariable("id") final Long branchId) {
        branchService.updateStatus(branchId);
        return "redirect:/branch_";
    }

}
