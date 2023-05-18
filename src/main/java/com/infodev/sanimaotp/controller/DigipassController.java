package com.infodev.sanimaotp.controller;

import com.infodev.sanimaotp.services.adminService.ListDigipassKeysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DigipassController {

    private final ListDigipassKeysService listDigipassKeysService;

    @Autowired
    public DigipassController(ListDigipassKeysService listDigipassKeysService) {
        this.listDigipassKeysService = listDigipassKeysService;
    }

    @GetMapping("/listDigipass_")
    public String listDigipass() {
        return "pages/listDigipass";
    }

    @ResponseBody
    @GetMapping("/listDigipass_/list")
    public ResponseEntity<?> digpassPagination(@RequestParam(name="start") final int offset,
                                               @RequestParam(name="length", defaultValue = "10") final int limit,
                                               @RequestParam(name="search[value]", defaultValue = "") final String searchKey,
                                               @RequestParam(name="draw", defaultValue = "0") final int draw) {
        return ResponseEntity.ok(listDigipassKeysService.digipassPagination(offset,limit,searchKey,draw));
    }

}
