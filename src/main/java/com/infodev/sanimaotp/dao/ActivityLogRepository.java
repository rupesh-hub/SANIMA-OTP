package com.infodev.sanimaotp.dao;

import com.infodev.sanimaotp.entities.DataLogger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivityLogRepository extends JpaRepository<DataLogger,Long> {
}

//
