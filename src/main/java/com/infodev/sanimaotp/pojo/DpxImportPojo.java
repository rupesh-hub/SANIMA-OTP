package com.infodev.sanimaotp.pojo;

import org.springframework.web.multipart.MultipartFile;

public class DpxImportPojo {
    private String initKey;
    private MultipartFile dpxFile;

    public DpxImportPojo(String initKey, MultipartFile dpxFile) {
        this.initKey = initKey;
        this.dpxFile = dpxFile;
    }

    public DpxImportPojo() {
    }

    public String getInitKey() {
        return initKey;
    }

    public void setInitKey(String initKey) {
        this.initKey = initKey;
    }

    public MultipartFile getDpxFile() {
        return dpxFile;
    }

    public void setDpxFile(MultipartFile dpxFile) {
        this.dpxFile = dpxFile;
    }
}
