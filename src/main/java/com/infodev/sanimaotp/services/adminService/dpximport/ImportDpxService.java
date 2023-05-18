package com.infodev.sanimaotp.services.adminService.dpximport;

import com.infodev.sanimaotp.pojo.GlobalResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ImportDpxService {
    public GlobalResponse importDpxService(MultipartFile file, String key) throws Exception;
}
