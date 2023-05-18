package com.infodev.sanimaotp.services.adminService;

import com.infodev.sanimaotp.entities.DigipassStatus;
import com.infodev.sanimaotp.pojo.DataTableResponsePojo;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface ListDigipassKeysService {

    List<DigipassStatus> getDigipassList();

    DataTableResponsePojo digipassPagination(final int offset, final int limit, final String searchKey, final int draw);

    int getOverAllCount(String countType);

    int getApplicationCount(int appId);

    Map getDashboardData();

    List<DigipassStatus> getAllDigipass();
}
