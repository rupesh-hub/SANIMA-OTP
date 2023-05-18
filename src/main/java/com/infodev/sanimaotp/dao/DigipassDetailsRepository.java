package com.infodev.sanimaotp.dao;

import com.infodev.sanimaotp.entities.DigipassDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface DigipassDetailsRepository extends JpaRepository<DigipassDetail,String> {

    @Query(value ="select * from digipass_detail dd where dd.serial_app_key like concat(?1,'%')",nativeQuery = true)
    DigipassDetail getDataBySerialAppKey (String serialAppKey);

    @Query(value="select * from digipass_detail d where d.serial_number=?1 order by serial_app_key",nativeQuery =true)
    List<DigipassDetail> getAllBlobsBySerialKey(String serialKey);


    @Modifying @Query(value="update digipass_detail set app_blob=?1 where rtrim(serial_app_key)=?2", nativeQuery = true)
    void updateAppBlob(String appBlob,String serialAppKey);

}
