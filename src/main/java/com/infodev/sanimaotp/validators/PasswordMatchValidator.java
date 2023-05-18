package com.infodev.sanimaotp.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.infodev.sanimaotp.pojo.ChangePasswordPojo;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, ChangePasswordPojo> {

    @Override
    public void initialize(PasswordMatch p) {

    }

    public boolean isValid(ChangePasswordPojo user, ConstraintValidatorContext c) {
        String password = user.getPassword();
        String confirmPassword = user.getConfirmPassword();

        if (password == null || !confirmPassword.equals(password)) {
            return false;
        }

        return true;
    }

}