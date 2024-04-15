package mappers.user;

import org.springframework.stereotype.Component;
import ru.teamfour.dao.entity.shelter.Shelter;
import ru.teamfour.dao.entity.shelter.ShelterDto;

@Component
public class ShelterMapper {

    public static ShelterDto shelterDto(Shelter shelter){
        return ShelterDto.builder()
                .id(shelter.getId())
                .typeAnimal(shelter.getTypeOfAnimal())
                .aboutShelter(shelter.getAboutShelter())
                .address(shelter.getAddress())
                .workSchedule(shelter.getWorkSchedule())
                .safetyMeasures(shelter.getSafetyMeasures())
                .securityData(shelter.getSecurityData())
                .build();

    }

    public static Shelter toShelter(ShelterDto shelterDto){
        return Shelter.builder()
                .id(shelterDto.getId())
                .typeOfAnimal(shelterDto.getTypeAnimal())
                .aboutShelter(shelterDto.getAboutShelter())
                .address(shelterDto.getAddress())
                .workSchedule(shelterDto.getWorkSchedule())
                .safetyMeasures(shelterDto.getSafetyMeasures())
                .securityData(shelterDto.getSecurityData())
                .build();
    }

}
