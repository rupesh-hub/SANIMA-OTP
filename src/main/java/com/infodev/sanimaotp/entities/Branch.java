package com.infodev.sanimaotp.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "branch")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Branch {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "branch_id_seq_gen")
    @SequenceGenerator(name = "branch_id_seq_gen", sequenceName = "branch_id_seq_gen", initialValue = 1, allocationSize = 1)
    @Column(name = "id", unique = true, nullable = false, updatable = false)
    private Long id;

    @Column(name = "name", length = 100, nullable = false, unique = true)
    private String name;

    @Column(name = "address", length = 500, nullable = false)
    private String address;

    @Column(name = "status", nullable = false)
    private Boolean status = true;

}
