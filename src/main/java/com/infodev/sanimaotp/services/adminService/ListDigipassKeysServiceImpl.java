package com.infodev.sanimaotp.services.adminService;

import com.infodev.sanimaotp.dao.DigipassStatusRepository;
import com.infodev.sanimaotp.entities.DigipassStatus;
import com.infodev.sanimaotp.pojo.DataTableResponsePojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ListDigipassKeysServiceImpl implements ListDigipassKeysService {

    private final DigipassStatusRepository digipassStatusRepository;

    @Autowired
    public ListDigipassKeysServiceImpl(DigipassStatusRepository digipassStatusRepository) {
        this.digipassStatusRepository = digipassStatusRepository;
    }

    @Override
    public List<DigipassStatus> getDigipassList() {
        List<DigipassStatus> digipassStatuses = digipassStatusRepository.listKeys();
        List finalList = new ArrayList();

        for (int i = 0; i < digipassStatuses.size(); i++) {
            Map map = new LinkedHashMap<>();
            map.put("SerialNumber", digipassStatuses.get(i).getSerialNumber());
            map.put("MobileId", digipassStatuses.get(i).getApp1Mb() == null ? "N/A" : digipassStatuses.get(i).getApp1Mb());
            map.put("KioskId", digipassStatuses.get(i).getApp2Kiosk() == null ? "N/A" : digipassStatuses.get(i).getApp2Kiosk());
            map.put("AtmId", digipassStatuses.get(i).getApp3Atm() == null ? "N/A" : digipassStatuses.get(i).getApp3Atm());
            map.put("VbvId", digipassStatuses.get(i).getApp4Vbv() == null ? "N/A" : digipassStatuses.get(i).getApp4Vbv());
            map.put("InternetBankId", digipassStatuses.get(i).getApp5Ibank() == null ? "N/A" : digipassStatuses.get(i).getApp5Ibank());
            map.put("Vbv_CR_Id", digipassStatuses.get(i).getApp6VbvCr() == null ? "N/A" : digipassStatuses.get(i).getApp6VbvCr());
            map.put("Kiosk_CR_Id", digipassStatuses.get(i).getApp7Kioskcr() == null ? "N/A" : digipassStatuses.get(i).getApp7Kioskcr());
            map.put("Central_Id", digipassStatuses.get(i).getCentralId() == null ? "N/A" : digipassStatuses.get(i).getCentralId());
            map.put("Status", digipassStatuses.get(i).getStatus());
            finalList.add(map);
        }

        return finalList;
    }

    @Override
    public DataTableResponsePojo digipassPagination(final int offset, final int limit, final String searchKey, final int draw) {

        Specification<DigipassStatus> spec = (root, query, criteriaBuilder) -> {

            if (searchKey.equals("") || searchKey == null)
                return null;

            return criteriaBuilder.or(
                    criteriaBuilder.like(root.get("serialNumber"), "%"+searchKey + "%"),
                    criteriaBuilder.like(root.get("status"), "%"+searchKey + "%"),
                    criteriaBuilder.like(root.get("centralId"), "%"+searchKey + "%"),
                    criteriaBuilder.like(root.get("app1Mb"), "%"+searchKey + "%"),
                    criteriaBuilder.like(root.get("app2Kiosk"), "%"+searchKey + "%"),
                    criteriaBuilder.like(root.get("app3Atm"), "%"+searchKey + "%"),
                    criteriaBuilder.like(root.get("app4Vbv"), "%"+searchKey + "%"),
                    criteriaBuilder.like(root.get("app5Ibank"), "%"+searchKey + "%"),
                    criteriaBuilder.like(root.get("app6VbvCr"), "%"+searchKey + "%"),
                    criteriaBuilder.like(root.get("app7Kioskcr"), "%"+searchKey + "%"),
                    criteriaBuilder.like(root.get("app8FtSig"), "%"+searchKey + "%")
            );
        };
        int page = offset/limit;
        log.info("limit {}, offset {} page {}",limit,offset,page);
        Page<DigipassStatus> digipassStatusPage = digipassStatusRepository.findAll(spec, PageRequest.of(page, limit)).map(this::mapDigipass);
        DataTableResponsePojo dataTableResponsePojo = DataTableResponsePojo.builder()
                .data(digipassStatusPage.getContent())
                .draw(new Integer(draw).toString())
                .recordsTotal(new Long(digipassStatusPage.getTotalElements()).toString())
                .recordsFiltered(new Long(digipassStatusPage.getTotalElements()).toString())
                .pages(new Integer(digipassStatusPage.getTotalPages()).toString())
                .build();
        return dataTableResponsePojo;
    }

    private DigipassStatus mapDigipass(final DigipassStatus digipassStatus) {

        DigipassStatus digipass = new DigipassStatus();
        digipass.setSerialNumber(digipassStatus.getSerialNumber());
        digipass.setApp1Mb(digipassStatus.getApp1Mb() == null ? "N/A" : digipassStatus.getApp1Mb());
        digipass.setApp2Kiosk(digipassStatus.getApp2Kiosk() == null ? "N/A" : digipassStatus.getApp2Kiosk());
        digipass.setApp3Atm(digipassStatus.getApp3Atm() == null ? "N/A" : digipassStatus.getApp3Atm());
        digipass.setApp4Vbv(digipassStatus.getApp4Vbv() == null ? "N/A" : digipassStatus.getApp4Vbv());
        digipass.setApp5Ibank(digipassStatus.getApp5Ibank() == null ? "N/A" : digipassStatus.getApp5Ibank());
        digipass.setApp6VbvCr(digipassStatus.getApp6VbvCr() == null ? "N/A" : digipassStatus.getApp6VbvCr());
        digipass.setApp7Kioskcr(digipassStatus.getApp7Kioskcr() == null ? "N/A" : digipassStatus.getApp7Kioskcr());
        digipass.setCentralId(digipassStatus.getCentralId() == null ? "N/A" : digipassStatus.getCentralId());
        digipass.setStatus(digipassStatus.getStatus());

        return digipass;
    }


    @Override
    public int getOverAllCount(String countType) {
        int count = 0;
        switch (countType) {
            case "N": {
                count = digipassStatusRepository.getOverallStat("N");
                break;
            }
            case "P": {
                count = digipassStatusRepository.getOverallStat("P");
                break;
            }
            case "A": {
                count = digipassStatusRepository.getOverallStat("A");
                break;
            }
        }
        return count;
    }

    @Override
    public int getApplicationCount(int appId) {
        int count = 0;
        switch (appId) {
            case 1: {
                count = digipassStatusRepository.getActiveApp1();
                break;
            }
            case 2: {
                count = digipassStatusRepository.getActiveApp2();
                break;
            }
            case 3: {
                count = digipassStatusRepository.getActiveApp3();
                break;
            }
            case 4: {
                count = digipassStatusRepository.getActiveApp4();
                break;
            }
            case 5: {
                count = digipassStatusRepository.getActiveApp5();
                break;
            }
            case 6: {
                count = digipassStatusRepository.getActiveApp6();
                break;
            }
            case 7: {
                count = digipassStatusRepository.getActiveApp7();
                break;
            }
            case 100: {
                count = digipassStatusRepository.getTotalCount();
                break;
            }
        }
        return count;
    }

    @Override
    public Map getDashboardData() {
        Map map = new LinkedHashMap();
        map.put("New", this.getOverAllCount("N"));
        map.put("Provided", this.getOverAllCount("P"));
        map.put("Active", this.getOverAllCount("A"));
        map.put("Total", this.getApplicationCount(100));
        map.put("ActiveMobile", this.getApplicationCount(1));
        map.put("ActivKiosk", this.getApplicationCount(2));
        map.put("ActiveATM", this.getApplicationCount(3));
        map.put("ActiveVBV", this.getApplicationCount(4));
        map.put("ActiveInternetBanking", this.getApplicationCount(5));
        map.put("ActiveVBV_CR", this.getApplicationCount(6));
        map.put("ActiveKIOSK_CR", this.getApplicationCount(7));
        return map;
    }

    @Override
    public List<DigipassStatus> getAllDigipass() {
        return digipassStatusRepository.findAll();
    }


}
