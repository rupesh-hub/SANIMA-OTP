package com.infodev.sanimaotp.pojo.UserManagement;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author ablok.shakya
 * @created 10/18/2022
 * @project sanimaotp
 */

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PasswordUpdatePojo {
    @NotNull
    private Integer userId;
    @NotEmpty
    private String password;
    @NotEmpty
    private String rePassword;
}
