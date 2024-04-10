package ru.teamfour.service.api.infoForAdoption;

import ru.teamfour.dao.entity.animal.TypeAnimal;
import ru.teamfour.dao.entity.infoForAdoption.InfoForAdoption;

public interface InfoForAdoptionService {
    public InfoForAdoption findInfoForAdoptionByTypeAnimal(TypeAnimal typeAnimal);
}
