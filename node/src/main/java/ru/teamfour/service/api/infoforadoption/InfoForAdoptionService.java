package ru.teamfour.service.api.infoforadoption;

import ru.teamfour.dao.entity.animal.TypeAnimal;
import ru.teamfour.dao.entity.infoforadoption.InfoForAdoption;

public interface InfoForAdoptionService {
    InfoForAdoption findInfoForAdoptionByTypeAnimal(TypeAnimal typeAnimal);
}