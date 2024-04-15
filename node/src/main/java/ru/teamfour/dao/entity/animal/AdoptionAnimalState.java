package ru.teamfour.dao.entity.animal;

/**
 * Статус усыновления
 */
public enum AdoptionAnimalState {
    /**
     * В процессе усыновления
     */
    PROCESS_OF_ADOPTION,
    /**
     * Усыновлен
     */
    ADOPTED,
    /**
     * Не усыновлен
     */
    NOT_ADOPTED
}
