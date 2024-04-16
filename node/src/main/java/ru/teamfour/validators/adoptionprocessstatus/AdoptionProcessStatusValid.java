package ru.teamfour.validators.adoptionprocessstatus;

import jakarta.validation.Constraint;
import jakarta.validation.constraints.NotBlank;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@NotBlank
@Constraint(validatedBy = AdoptionProcessStatusValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface AdoptionProcessStatusValid {
    String message() default "Invalid adoption animal";

}