package com.infodev.sanimaotp.dao;

import com.infodev.sanimaotp.entities.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLogRepository extends JpaRepository<UserLog, Long> {
    @Query(
            value = "select u from UserLog u order by u.rDateTime desc "
    )
    List<UserLog> findAllByOrderByRDateTimeDesc();
}
