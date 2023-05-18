package com.infodev.sanimaotp.entities;

import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@DynamicUpdate
@Builder
public class UserLog {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="r_datetime")
    private LocalDateTime rDateTime;

    @Column(name="task_by")
    private int taskBy;

    @Column(name="task_type")
    private String taskType;

    @Column(name="extra_info_1")
    private String extraInfo1;

    @Column(name="extra_info_2")
    private String extraInfo2;

    @Column(name="extra_info_3")
    private String extraInfo3;

}
