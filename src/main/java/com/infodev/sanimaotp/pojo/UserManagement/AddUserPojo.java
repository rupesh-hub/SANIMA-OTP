package com.infodev.sanimaotp.pojo.UserManagement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AddUserPojo {

    private Integer userId;
    private String userName;
    private String password;
    private int status;
    private String[] roleList;
    private boolean requiredPassChange;
    private String branchName;

}
