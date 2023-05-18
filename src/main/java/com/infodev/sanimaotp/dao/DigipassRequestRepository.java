package com.infodev.sanimaotp.dao;

import com.infodev.sanimaotp.entities.DigipassRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DigipassRequestRepository extends JpaRepository<DigipassRequest, Long> {

    @Query(nativeQuery = true, value = "select * from digipass_request dr where lower(dr.approval_status) = lower('pending') and dr.branch_id = ?1")
    List<DigipassRequest> allDigiRequest(Long branchId);

    @Query(nativeQuery = true, value = "select * from digipass_request dr where lower(dr.approval_status) = lower('pending')")
    List<DigipassRequest> digipassPendingList();

    @Query(nativeQuery = true, value = "select * from digipass_request dr where lower(approval_status) = lower('approved') and qr_generated_status = false")
    List<DigipassRequest> findAllByApprovedStatus();

    Optional<DigipassRequest> findByCentralId(final String centralId);

    @Query(nativeQuery = true,
            value = "select * from digipass_request where lower(central_id) = lower(?1) " +
                    "and lower(application_id) = lower(?2) " +
                    "and lower(application_type) = lower(?3)")
    Optional<DigipassRequest> getDigipass(final String cID, final String aID, final String aType);

    Optional<DigipassRequest> findByCentralIdAndApplicationId(String centralId, String applicationId);

    @Query(nativeQuery = true, value = "select * from digipass_request dr where dr.branch_id = ?1 order by 1")
    List<DigipassRequest> findAll(final Long branchId);

    @Query(nativeQuery = true, value = "select * from digipass_request dr where dr.branch_id = ?1 order by dr.id desc")
    Page<DigipassRequest> findAllForBranch(Long branchId, PageRequest of);

    @Query(nativeQuery = true, value = "select * from digipass_request dr order by dr.id desc")
    Page<DigipassRequest> findAllForAdmin(PageRequest of);
}
