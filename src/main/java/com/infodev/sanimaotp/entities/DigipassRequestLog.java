package com.infodev.sanimaotp.entities;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="digipass_request_log")
@DynamicUpdate
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class DigipassRequestLog {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "digipass_req_log_seq_gen")
    @SequenceGenerator(name = "digipass_req_log_seq_gen", sequenceName = "digipass_req_log_seq_gen", initialValue = 1, allocationSize = 1)
    @Column(name="id", nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(name="digipass_request_id", nullable = false)
    private Long digipassRequestId;

    @Column(name = "action_date", nullable = false)
    private LocalDate actionDate;

    @Column(name="action_type", length = 100, nullable = false)
    private String actionType;

    @Column(name="extra_detail_1", length = 1000)
    private String extraDetail1;

    @Column(name="extra_detail_2", length = 1000)
    private String extraDetail2;

    @Column(name="extra_detail_3", length = 10000)
    private String extraDetail3;

}
