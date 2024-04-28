package ru.teamfour.service.impl.shelter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.teamfour.dao.entity.animal.Animal;
import ru.teamfour.dao.entity.animal.TypeAnimal;
import ru.teamfour.dao.entity.shelter.Shelter;
import ru.teamfour.mappers.shelter.ShelterAddDtoMapper;
import ru.teamfour.mappers.shelter.ShelterDtoMapper;
import ru.teamfour.repositories.ShelterRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static ru.teamfour.dao.entity.animal.AdoptionAnimalState.NOT_ADOPTED;

@ExtendWith(MockitoExtension.class)
class ShelterServiceImplTest {

    @Mock
    private ShelterRepository shelterRepositoryMock;

    @Mock
    private ShelterAddDtoMapper shelterAddDtoMapperMock;

    @Mock
    private ShelterDtoMapper shelterDtoMapperMock;

    @InjectMocks
    private ShelterServiceImpl shelterService;

    public static final String EXPECTED_UUID = String.valueOf(UUID.fromString("6549c2ee-0fb2-43aa-9fc4-42f4775a1586"));
    public static final String EXPECTED_NAME = "Верный друг";
    public static final String EXPECTED_ILLEGAL = String.valueOf(UUID.fromString("6549c2ee-0fb2-43aa-9fc4-42f4775a1586"));


    List<Animal> animals = new ArrayList<>();
    List<Shelter> shelters = new ArrayList<>();
    Shelter shelter = new Shelter();
    Animal animal = new Animal();

    @BeforeEach
    void setUp() {

        animal.setShelter(shelter);
        animals.add(animal);
        shelter.setAnimals(animals);
        shelter.setId(UUID.fromString("6549c2ee-0fb2-43aa-9fc4-42f4775a1586"));
        shelter.setName("Верный друг");
        shelter.setTypeOfAnimal(TypeAnimal.DOG);
        shelter.setAboutShelter("Собачий приют номер 2");
        shelter.setAddress("ул. Космическая, 72, г. Иркутск");
        shelter.setWorkSchedule("Понедельник-пятница:\n\t\tУтро: 10:00 - 12:00 (прием посетителей, уборка вольеров)\n\t\tПолдень: 12:00 - 14:00 (перерыв на обед)\n\t\tВечер: 14:00 - 17:00 (прием посетителей, прогулки с собаками)\n\t\tСуббота:\n\t\tУтро: 10:00 - 13:00 (прием посетителей, проведение мероприятий)\n\t\tПолдень: 13:00 - 15:00 (перерыв на обед)\n\t\tВечер: 15:00 - 17:00 (прием посетителей, волонтерская работа)\nВоскресенье: Выходной день для посетителей, но возможны специальные мероприятия и программы по усыновлению.");
        shelter.setSafetyMeasures("Общие правила:<br><tab>1. Соблюдайте тишину и спокойствие, чтобы не пугать или беспокоить животных и других посетителей.<br><tab>2. Следите за своими детьми и держите их под присмотром, чтобы они не трогали животных без разрешения.<br><tab>3. Соблюдайте чистоту и порядок на территории приюта, не бросайте мусор и не загромождайте общественные пространства.<br>Правила отношения к животным:<br><tab>1. Не пытайтесь проникнуть в вольеры или загончики без разрешения персонала приюта.<br><tab>2. Обращайтесь с животными с уважением и осторожностью, не нависайте над ними или не пугайте их внезапными движениями.<br><tab>3. Не кормите животных своей едой без разрешения сотрудников приюта, так как это может навредить их здоровью.<br>Правила взаимодействия с персоналом приюта:<br><tab>1. Обращайтесь к персоналу приютов с уважением и дружелюбием, задавайте вопросы и просите помощи, если это необходимо.<br><tab>2. Следуйте инструкциям персонала приюта, особенно касающимся безопасности и правил поведения на территории.<br><tab>3. Помогайте персоналу поддерживать порядок и безопасность, сообщая о любых наблюдаемых инцидентах или проблемах.");
        shelter.setSecurityData("Имя: Спелова Вероника. Должность: Вахтер\nТелефон: +7 (XXX) XXX-XXXX\nЭлектронная почта: security@lapochkihaus.com");
        animal.setId(UUID.fromString("0deae04a-ce9b-4b4b-afb0-bd87a8c92b51"));
        animal.setTypeAnimal(TypeAnimal.DOG);
        animal.setName("Анчар");
        animal.setAge(2.0);
        animal.setBreed("Джек-рассел-терьер;");
        animal.setHabits("Жизнерадостный и преданный, весёлый, неугомонный компаньон, готового вести активный образ жизни.");
        animal.setAdopted(NOT_ADOPTED);
    }

    @Test
    void getShelterFindByUuid() {
        Mockito.when(shelterRepositoryMock.findById(any(UUID.class))).thenReturn(Optional.of(shelter));
        UUID actual = shelterService.findById(shelter.getId()).getId();
        assertEquals(EXPECTED_UUID, actual.toString());
    }

    @Test
    public void shouldThrowIllegalArgumentExceptionWhenUuidContainsIllegalCharacters() {
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> shelterService.findById(UUID.fromString(EXPECTED_ILLEGAL))
        );
    }

    @Test
    void getShelterFindByName() {
        Mockito.when(shelterRepositoryMock.findByName(any(String.class))).thenReturn(Optional.of(shelter));
        String actual = shelterService.findByName(shelter.getName()).getName();
        assertEquals(EXPECTED_NAME, actual);
    }

    @Test
    void shouldRemoveObjectByUuid() {
        shelterService.remove(shelter.getId());
        verify(shelterRepositoryMock, times(1)).deleteById(any(UUID.class));

    }

    @Test
    void shouldFindByNameDtoOnThrow() {
        when(shelterRepositoryMock.findByName(any(String.class))).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> shelterService.findByNameDto("Нет"));
    }

    @Test
    void shouldUpdateOnThrow() {
        when(shelterRepositoryMock.findById(any(UUID.class))).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> shelterService.findById(UUID.fromString(EXPECTED_UUID)));
    }

    @Test
    void shouldFindByNameOnThrow() {
        Mockito.when(shelterRepositoryMock.findByName(any(String.class))).thenThrow(IllegalArgumentException.class);
        assertThrows(IllegalArgumentException.class, () -> shelterService.findByName(EXPECTED_NAME));
    }


}