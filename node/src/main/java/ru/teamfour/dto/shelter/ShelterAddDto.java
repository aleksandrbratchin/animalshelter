package ru.teamfour.dto.shelter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import ru.teamfour.dao.entity.animal.TypeAnimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShelterAddDto {

    @NotBlank
    private String name;

    @NotNull
    private TypeAnimal typeAnimal;

    @Positive
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
