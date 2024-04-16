package ru.teamfour.validators.adoptionanimalstate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.teamfour.dao.entity.animal.AdoptionAnimalState;

public class AdoptionAnimalStateValidator implements ConstraintValidator<AdoptionAnimalStateValid, String> {

    @Override
    public void initialize(AdoptionAnimalStateValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean correct = true;
        try {
            AdoptionAnimalState.valueOf(value);
        } catch (IllegalArgumentException e) {
            correct = false;
        }
        return correct;
    }

}
