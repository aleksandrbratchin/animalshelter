package ru.teamfour.dao.entity.adoptionanimal;

/**
 * Статус усыновления
 */
public enum AdoptionStatus {
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
