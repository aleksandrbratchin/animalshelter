package ru.teamfour.validators.roleuser;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.teamfour.dao.entity.user.RoleUser;

public class RoleUserValidator implements ConstraintValidator<ValidRoleUser, String> {

    @Override
    public void initialize(ValidRoleUser constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean hasRole = true;
        try {
            RoleUser roleUser = RoleUser.valueOf(value);
        } catch (IllegalArgumentException e) {
            hasRole = false;
        }
        return hasRole;
    }

}
