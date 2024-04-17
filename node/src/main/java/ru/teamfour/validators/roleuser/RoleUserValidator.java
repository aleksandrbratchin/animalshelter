package ru.teamfour.validators.roleuser;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.teamfour.dao.entity.user.RoleUser;

public class RoleUserValidator implements ConstraintValidator<RoleUserValid, String> {

    @Override
    public void initialize(RoleUserValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean correct = true;
        try {
            RoleUser.valueOf(value);
        } catch (IllegalArgumentException e) {
            correct = false;
        }
        return correct;
    }

}
