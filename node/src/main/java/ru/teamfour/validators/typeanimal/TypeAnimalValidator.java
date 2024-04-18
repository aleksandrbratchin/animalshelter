package ru.teamfour.validators.typeanimal;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.teamfour.dao.entity.animal.TypeAnimal;

public class TypeAnimalValidator implements ConstraintValidator<TypeAnimalValid, TypeAnimal> {

    @Override
    public void initialize(TypeAnimalValid typeAnimalValid) {
    }

    @Override
    public boolean isValid(TypeAnimal value, ConstraintValidatorContext context) {
        boolean correct = true;
        try {
            TypeAnimal.valueOf(value.name());
        } catch (IllegalArgumentException e) {
            correct = false;
        }
        return correct;
    }

}
