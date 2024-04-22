package ru.teamfour.mappers.adoptionanimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.beans.factory.annotation.Autowired;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessAnimal;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessStatus;
import ru.teamfour.dto.adoptionanimal.AdoptionProcessAnimalCreateDto;
import ru.teamfour.service.api.animal.AnimalService;
import ru.teamfour.service.api.shelter.ShelterService;
import ru.teamfour.service.impl.user.UserService;

@Mapper(componentModel = "spring")
@Getter
@Setter
@NoArgsConstructor
public abstract class AdoptionProcessAnimalMapper {

    @Autowired
    protected ShelterService shelterService;

    @Autowired
    protected UserService userService;

    @Autowired
    protected AnimalService animalService;

    protected AdoptionProcessStatus adoptionProcessStatus = AdoptionProcessStatus.PROCESS_ADOPTION;

    @Mappings({
            @Mapping(target = "animal", expression = "java(animalService.findById(dto.getIdAnimal()))"),
            @Mapping(target = "user", expression = "java(userService.getUser(dto.getIdUser()))"),
            @Mapping(target = "shelter", expression = "java(shelterService.findById(dto.getIdShelter()))"),
            @Mapping(target = "adoptionProcessStatus", expression = "java(adoptionProcessStatus)")
    })
    public abstract AdoptionProcessAnimal toAnimal(AdoptionProcessAnimalCreateDto dto);
}
