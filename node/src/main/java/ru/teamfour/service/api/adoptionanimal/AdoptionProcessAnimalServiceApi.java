package ru.teamfour.service.api.adoptionanimal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import ru.teamfour.dao.entity.adoptionanimal.AdoptionProcessAnimal;
import ru.teamfour.dto.adoptionanimal.AdoptionProcessAnimalCreateDto;
import ru.teamfour.dto.adoptionanimal.AdoptionProcessAnimalInfoDto;

import java.util.List;
import java.util.UUID;

/***
 * Для процесса усыновления животных
 */
public interface AdoptionProcessAnimalServiceApi {

    /***
     * Усыновления животныз по Id
     *
     * @param id {@link UUID}
     */
    AdoptionProcessAnimal findById(@NotNull UUID id);

    /***
     * Создания процесса усыновления
     *
     * @param adoptionProcessAnimalCreateDto {@link AdoptionProcessAnimalCreateDto}
     */
    AdoptionProcessAnimalInfoDto createAdoption(@Valid AdoptionProcessAnimalCreateDto adoptionProcessAnimalCreateDto);

    /***
     * Продления процесса усыновления на 14 дней
     *
     * @param id {@link UUID}
     */
    AdoptionProcessAnimalInfoDto addfourteendays(@NotNull UUID id);

    /***
     * Продления процесса усыновления на 30 дней
     *
     * @param id {@link UUID}
     */
    AdoptionProcessAnimalInfoDto addthirtydays(@NotNull UUID id);

    /***
     * Одобрения процесса усыновления
     *
     * @param id {@link UUID}
     */
    AdoptionProcessAnimalInfoDto approved(@NotNull UUID id);

    /***
     * Отклонения процесса усыновления
     *
     * @param id {@link UUID}
     */
    AdoptionProcessAnimalInfoDto rejected(@NotNull UUID id);

    /***
     * Поиска всех активных усыновлений
     *
     */
    List<AdoptionProcessAnimalInfoDto> receiveAdoptionsOnWhichADecisionNeedsToBeMade();
}
