package ru.teamfour.service.impl.shelter;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.teamfour.dao.entity.shelter.Shelter;
import ru.teamfour.repositories.ShelterRepository;
import ru.teamfour.service.api.shelter.ShelterService;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class ShelterServiceImpl implements ShelterService {
    private final ShelterRepository shelterRepository;

    /**
     * метод добавляет в БД объект {@link Shelter}
     *
     * @param shelter метод принимает в качестве параметра объект Shelter
     * @return метод возвращает объект, записанный в БД
     */
    @Override
    public Shelter add(Shelter shelter) {

        return shelterRepository.save(shelter);
    }

    /**
     * метод удаляет из БД объект {@link Shelter} по UUID
     *
     * @param id принимет UUID удаляемого объекта
     */
    @Override
    public void remove(UUID id) {
        shelterRepository.deleteById(id);
    }

    /**
     * метод находит объект {@link Shelter} в БД по UUID
     *
     * @param id UUID
     * @return возвращает найденный объект
     */
    @Override
    public Shelter find(UUID id) {
        return shelterRepository.findById(id).orElseThrow(RuntimeException::new);//todo
    }

    /**
     * метод находит объект {@link Shelter} в БД по названию приюта
     *
     * @param name название приюта
     * @return {@link Shelter} с названием {@code name}
     */
    @Override
    public Shelter findByName(String name) {
        return shelterRepository.findByName(name).orElseThrow(RuntimeException::new);//todo
    }

    @Override
    public List<Shelter> findAll() {
        return shelterRepository.findAll();
    }

    /**
     * метод заменяет объект в БД на переданный в параметре
     *
     * @param id      тип UUID
     * @param shelter объект класса {@link Shelter}
     * @return возвращает переданный объект
     */
    @Override
    public Shelter change(UUID id, Shelter shelter) {
        return shelterRepository.save(shelter);

    }
}
