package com.infodev.sanimaotp.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class RolePojo {
    private int id;
    private String name;
    private String description;
    private int status;
}
