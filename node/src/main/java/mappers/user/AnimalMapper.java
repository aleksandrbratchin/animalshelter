package mappers.user;

import org.springframework.stereotype.Component;
import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.dao.entity.animal.AnimalDto;

@Component
public class AnimalMapper {

    public static AnimalDto animalDto(Animal animal) {
        return AnimalDto.builder()
                .id(animal.getId())
                .name(animal.getName())
                .typeAnimal(animal.getTypeOfAnimal())
                .age(Double.valueOf(String.valueOf(animal.getAge())))
                .breed(animal.getBreed())
                .habits(animal.getHabits())
                .adopted(String.valueOf(animal.isAdopted()))
                .build();
    }

    public static Animal toAminal(AnimalDto animalDto){
        return Animal.builder()
                .id(animalDto.getId())
                .typeOfAnimal(animalDto.getTypeAnimal())
                .age(animalDto.getAge())
                .breed(animalDto.getBreed())
                .habits(animalDto.getHabits())
                .adopted(Boolean.parseBoolean(animalDto.getAdopted()))
                .build();
    }

}
