package ru.teamfour.dao.entity.adoptionanimal;

/**
 * Статус усыновления
 */
public enum AdoptionProcessStatus {
    /**
     * В процессе усыновления
     */
    PROCESS_ADOPTION,
    /**
     * Усыновлен
     */
    ADOPTED,
    /**
     * Не усыновлен
     */
    NOT_ADOPTED,
    /**
     * Усыновление отклонено
     */
    ADOPTION_DENIED
}
