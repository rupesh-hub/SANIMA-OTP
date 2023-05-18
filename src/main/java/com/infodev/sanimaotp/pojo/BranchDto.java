package com.infodev.sanimaotp.pojo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BranchDto {

    private Long id;
    private String name;
    private String address;
    private Boolean status = true;

}
