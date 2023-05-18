package com.infodev.sanimaotp.entities;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@Table(name="modules")
@DynamicUpdate
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "module_seq_gen")
    @SequenceGenerator(name = "module_seq_gen", sequenceName = "module_seq_gen", initialValue = 1, allocationSize = 1)
    @Column(name="id", nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(name="module_name", length = 100, nullable = false, unique = true)
    private String moduleName;

    @Column(name="module_abbr", length = 10, nullable = false, unique = true)
    private String moduleAbbreviation;

    @Column(name="remarks", length = 1000)
    private String remarks;

    @Column(name="status")
    private int status;

}
