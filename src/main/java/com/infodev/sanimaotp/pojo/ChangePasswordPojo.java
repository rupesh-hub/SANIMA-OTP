package com.infodev.sanimaotp.pojo;

import com.infodev.sanimaotp.validators.PasswordMatch;
import lombok.*;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@PasswordMatch(message="password and confirm password does not matched.")
public class ChangePasswordPojo {

    @NotEmpty
    private String password;
    @NotEmpty
    private String confirmPassword;

}
