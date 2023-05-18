package com.infodev.sanimaotp.dao;

import com.infodev.sanimaotp.entities.DigipassDetailBackup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DigipassDetailsBackupRepository extends JpaRepository<DigipassDetailBackup,String> {

    @Query(value ="select * from digipass_detail dd where dd.serial_app_key like concat(?1,'%')",nativeQuery = true)
    DigipassDetailBackup getDataBySerialAppKey (String serialAppKey);

    @Query(value="select * from digipass_detail d where d.serial_number=?1",nativeQuery =true)
    List<DigipassDetailBackup> getAllBlobsBySerialKey(String serialKey);

}
