package ru.teamfour.controller.advice.shelter;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.teamfour.controller.ShelterController;

/***
 * обработка исключений которые могут возникнуть в контроллере {@link ShelterController} не связанные с валидацией
 */
@RestControllerAdvice(assignableTypes = {ShelterController.class})
public class ShelterControllerAdvice {

    //todo обработать исключения

}
