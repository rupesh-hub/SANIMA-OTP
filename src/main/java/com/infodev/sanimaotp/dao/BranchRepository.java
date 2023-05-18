package com.infodev.sanimaotp.dao;

import com.infodev.sanimaotp.entities.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BranchRepository extends JpaRepository<Branch, Long> {

    @Query( value = "select * from branch b where lower(b.name) = lower(?1) and status = true", nativeQuery = true)
    Optional<Branch> findByName(final String branchName);

    @Query(value = "select * from branch b where status = true", nativeQuery = true)
    List<Branch> fetchAll();
}
