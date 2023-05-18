package com.infodev.sanimaotp.pojo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ModulePojo {

    private Long id;
    private String moduleName;
    private String moduleAbbreviation;
    private String remarks;
    private int status;

}
