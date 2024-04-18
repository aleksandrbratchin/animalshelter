package ru.teamfour.validators.adoptionanimalstate;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessStatus;
import ru.teamfour.dao.entity.animal.AdoptionAnimalState;
import ru.teamfour.dao.entity.user.RoleUser;

public class AdoptionAnimalStateValidator implements ConstraintValidator<AdoptionAnimalStateValid, AdoptionAnimalState> {

    @Override
    public void initialize(AdoptionAnimalStateValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(AdoptionAnimalState value, ConstraintValidatorContext context) {
        boolean correct = true;
        try {
            AdoptionAnimalState.valueOf(value.name());
        } catch (IllegalArgumentException e) {
            correct = false;
        }
        return correct;
    }

}
