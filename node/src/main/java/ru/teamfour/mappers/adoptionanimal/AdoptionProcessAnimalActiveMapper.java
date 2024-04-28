package ru.teamfour.mappers.adoptionanimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessAnimal;
import ru.teamfour.dto.adoptionanimal.AdoptionProcessAnimalActiveDto;
import ru.teamfour.dto.adoptionanimal.AdoptionProcessAnimalInfoDto;
import ru.teamfour.mappers.animal.AnimalMapper;
import ru.teamfour.mappers.user.UserInfoMapper;

@Mapper(componentModel = "spring", uses = {UserInfoMapper.class, AnimalMapper.class})
@Getter
@Setter
@NoArgsConstructor
public abstract class AdoptionProcessAnimalActiveMapper {

//    @Mapping(source = "date", target = "date", dateFormat = "dd.MM.yyyy")
//    public abstract AdoptionProcessAnimalActiveDto toDto(AdoptionProcessAnimal processAnimal);


}
