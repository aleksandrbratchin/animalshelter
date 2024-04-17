package ru.teamfour.dto.shelter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import ru.teamfour.dao.entity.animal.TypeAnimal;
import ru.teamfour.dto.animal.AnimalDto;
import ru.teamfour.validators.typeanimal.TypeAnimalValid;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShelterInfoDto {

    @NotNull(message = "UUID cannot be null")
    private UUID id;

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

    private List<AnimalDto> animals;

}
