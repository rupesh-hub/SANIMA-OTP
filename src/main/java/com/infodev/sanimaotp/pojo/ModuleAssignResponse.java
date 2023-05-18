package com.infodev.sanimaotp.pojo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Setter
@Getter
@Builder
public class ModuleAssignResponse {

    private Integer roleId;
    private String roleName;
    private List<String> moduleAbbrList;

}
