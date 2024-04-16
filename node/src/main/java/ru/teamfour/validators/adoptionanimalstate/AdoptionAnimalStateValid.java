package ru.teamfour.validators.adoptionanimalstate;

import jakarta.validation.Constraint;
import jakarta.validation.constraints.NotBlank;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@NotBlank
@Constraint(validatedBy = AdoptionAnimalStateValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface AdoptionAnimalStateValid {
    String message() default "Invalid adoption animal";

}
