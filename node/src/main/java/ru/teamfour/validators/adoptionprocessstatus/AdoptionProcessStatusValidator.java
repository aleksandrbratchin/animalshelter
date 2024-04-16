package ru.teamfour.validators.adoptionprocessstatus;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessStatus;

public class AdoptionProcessStatusValidator implements ConstraintValidator<AdoptionProcessStatusValid, String> {

    @Override
    public void initialize(AdoptionProcessStatusValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean correct = true;
        try {
            AdoptionProcessStatus.valueOf(value);
        } catch (IllegalArgumentException e) {
            correct = false;
        }
        return correct;
    }

}
