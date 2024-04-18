package ru.teamfour.service.api.infoforadoption;

import lombok.NonNull;
import ru.teamfour.dao.entity.animal.TypeAnimal;
import ru.teamfour.dao.entity.infoforadoption.InfoForAdoption;

public interface InfoForAdoptionService {
    InfoForAdoption findInfoForAdoptionByTypeAnimal(@NonNull TypeAnimal typeAnimal);
}
