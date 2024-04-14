package ru.teamfour.dao.entity.animal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ru.teamfour.validators.roleuser.ValidRoleUser;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnimalDto {

    @NotNull(message = "UUID cannot be null")
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private TypeAnimal typeAnimal;

    @NotBlank
    private Double age;

    @NotBlank
    private String breed;

    @NotBlank
    private String habits;

    @NotBlank
    private String adopted;

}
