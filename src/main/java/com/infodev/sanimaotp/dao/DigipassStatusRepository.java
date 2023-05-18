package com.infodev.sanimaotp.dao;

import com.infodev.sanimaotp.entities.DigipassStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface DigipassStatusRepository extends JpaRepository<DigipassStatus, String>, JpaSpecificationExecutor<DigipassStatus> {

    @Query(value = "select * from digipass_status d where d.central_id=?1", nativeQuery = true)
    DigipassStatus getStatusRowByCentralId(String centralId);

    @Query(value = "select * from digipass_status d where d.status='N' ORDER BY serial_number LIMIT 1", nativeQuery = true)
    DigipassStatus getFreeSerialKey();

    @Query(value = "select * from digipass_status d where d.central_id=?1 and d.status=?2", nativeQuery = true)
    DigipassStatus getSerialRowByStatus(String centralId, String status);

    @Query(value = "select * from digipass_status", nativeQuery = true)
    List<DigipassStatus> listKeys();

    @Query(value = "select count(*) from digipass_status where status=?1", nativeQuery = true)
    int getOverallStat(String st);

    @Query(value = "SELECT count(*) FROM digipass_status ds where ds.app1_mb IS NOT NULL and ds.status='A'", nativeQuery = true)
    int getActiveApp1();

    @Query(value = "SELECT count(*) FROM digipass_status ds where ds.app2_kiosk IS NOT NULL and ds.status='A'", nativeQuery = true)
    int getActiveApp2();

    @Query(value = "SELECT count(*) FROM digipass_status ds where ds.app3_atm IS NOT NULL and ds.status='A'", nativeQuery = true)
    int getActiveApp3();

    @Query(value = "SELECT count(*) FROM digipass_status ds where ds.app4_vbv IS NOT NULL and ds.status='A'", nativeQuery = true)
    int getActiveApp4();

    @Query(value = "SELECT count(*) FROM digipass_status ds where ds.app5_ibank IS NOT NULL and ds.status='A'", nativeQuery = true)
    int getActiveApp5();

    @Query(value = "SELECT count(*) FROM digipass_status ds where ds.app6_vbv_cr IS NOT NULL and ds.status='A'", nativeQuery = true)
    int getActiveApp6();

    @Query(value = "SELECT count(*) FROM digipass_status ds where ds.app7_kioskcr IS NOT NULL and ds.status='A'", nativeQuery = true)
    int getActiveApp7();

    @Query(value = "SELECT count(*) FROM digipass_status", nativeQuery = true)
    int getTotalCount();

}
