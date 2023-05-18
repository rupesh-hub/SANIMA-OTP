package com.infodev.sanimaotp.pojo;

import com.infodev.sanimaotp.entities.Branch;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserPojo {

    private int userId;
    private String username;
    private String role;
    private int status;
    private boolean requiredPassChange;
    private Branch branch;
    private String branchName;
}
