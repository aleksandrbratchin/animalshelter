package ru.teamfour.mappers.adoptionanimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mapstruct.Mapper;
import ru.teamfour.mappers.animal.AnimalMapper;
import ru.teamfour.mappers.user.UserInfoMapper;

@Mapper(componentModel = "spring", uses = {UserInfoMapper.class, AnimalMapper.class})
@Getter
@Setter
@NoArgsConstructor
public abstract class AdoptionProcessAnimalActiveMapper {

}
