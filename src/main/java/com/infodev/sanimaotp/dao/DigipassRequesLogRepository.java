package com.infodev.sanimaotp.dao;

import com.infodev.sanimaotp.entities.DigipassRequestLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DigipassRequesLogRepository extends JpaRepository<DigipassRequestLog, Long> {

}
