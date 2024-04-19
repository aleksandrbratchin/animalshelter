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
import ru.teamfour.dto.adoptionanimal.AdoptionProcessAnimalInfoDto;
import ru.teamfour.dto.shelter.ShelterInfoDto;
import ru.teamfour.dto.user.UserInfoDto;
import ru.teamfour.mappers.animal.AnimalMapper;
import ru.teamfour.mappers.user.UserInfoMapper;
import ru.teamfour.service.api.animal.AnimalService;
import ru.teamfour.service.api.shelter.ShelterService;
import ru.teamfour.service.impl.user.UserService;

@Mapper(componentModel = "spring", uses = {UserInfoMapper.class, AnimalMapper.class})
@Getter
@Setter
@NoArgsConstructor
public abstract class AdoptionProcessAnimalInfoMapper {

    @Mapping(source = "date", target = "date", dateFormat = "dd.MM.yyyy")
    public abstract AdoptionProcessAnimalInfoDto toDto(AdoptionProcessAnimal processAnimal);
}
