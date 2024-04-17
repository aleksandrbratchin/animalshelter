package ru.teamfour.dto.shelter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import ru.teamfour.dao.entity.animal.TypeAnimal;
import ru.teamfour.validators.typeanimal.TypeAnimalValid;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShelterAddDto {

    @NotBlank
    private String name;

    @TypeAnimalValid
    private TypeAnimal typeAnimal;

    @NotBlank
    private String aboutShelter;

    @NotBlank
    private String address;

    @NotBlank
    private String workSchedule;

    @NotBlank
    private String safetyMeasures;

    @NotBlank
    private String securityData;

}
