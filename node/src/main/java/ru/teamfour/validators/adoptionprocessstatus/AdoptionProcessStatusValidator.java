package ru.teamfour.validators.adoptionprocessstatus;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessStatus;
import ru.teamfour.dao.entity.user.RoleUser;

public class AdoptionProcessStatusValidator implements ConstraintValidator<AdoptionProcessStatusValid, AdoptionProcessStatus> {

    @Override
    public void initialize(AdoptionProcessStatusValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(AdoptionProcessStatus value, ConstraintValidatorContext context) {
        boolean correct = true;
        try {
            AdoptionProcessStatus.valueOf(value.name());
        } catch (IllegalArgumentException e) {
            correct = false;
        }
        return correct;
    }

}
