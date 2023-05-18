package com.infodev.sanimaotp.pojo;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ModuleAssignRequest {

    @NotNull
    private Integer roleId;
    @NotEmpty
    private List<Long> modules;

}
