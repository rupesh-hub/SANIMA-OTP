package com.infodev.sanimaotp.dao;

import com.infodev.sanimaotp.entities.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    @Query(nativeQuery = true, value = "select * from user_role where user_id = ?1")
    Optional<UserRole> findByUserId(Integer userId);

}
