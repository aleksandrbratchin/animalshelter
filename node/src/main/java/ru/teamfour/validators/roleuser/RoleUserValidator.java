package ru.teamfour.validators.roleuser;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.teamfour.dao.entity.animal.TypeAnimal;
import ru.teamfour.dao.entity.user.RoleUser;

public class RoleUserValidator implements ConstraintValidator<RoleUserValid, RoleUser> {

    @Override
    public void initialize(RoleUserValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(RoleUser value, ConstraintValidatorContext context) {
        boolean correct = true;
        try {
            RoleUser.valueOf(value.name());
        } catch (IllegalArgumentException e) {
            correct = false;
        }
        return correct;
    }

}
