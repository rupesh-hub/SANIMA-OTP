package com.infodev.sanimaotp.entities;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name = "digipass_request")
@DynamicUpdate
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DigipassRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "digipass_req_seq_gen")
    @SequenceGenerator(name = "digipass_req_seq_gen", sequenceName = "digipass_req_seq_gen", initialValue = 1, allocationSize = 1)
    @Column(name = "id", unique = true, updatable = false)
    private Long id;

    @Column(name = "central_id", length = 250, nullable = false)
    private String centralId;

    @Column(name = "application_id", length = 250, nullable = false)
    private String applicationId;

    @Column(name = "application_type", nullable = false)
    private String applicationType;

    @Column(name = "status")
    private int status;

    @Column(name = "approval_status", length = 100, nullable = false)
    private String approvalStatus;//PENDING/APPROVE/REJECT

    @Column(name = "remarks", length = 1000)
    private String remarks;

    @Column(name = "to_be_approved_by")
    private int toBeApprovedBy;

    @Column(name = "qr_generated_status")
    private boolean qrGeneratedStatus = false;

    @Column(name = "branch_id", nullable = false)
    private Long branchId;

}
