package ru.teamfour.service.impl.infoForAdoption;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.teamfour.dao.entity.animal.TypeAnimal;
import ru.teamfour.dao.entity.infoForAdoption.InfoForAdoption;
import ru.teamfour.repositories.InfoForAdoptionRepository;
import ru.teamfour.service.api.infoForAdoption.InfoForAdoptionService;
@Service
@AllArgsConstructor
public class InfoForAdoptionServiceImpl implements InfoForAdoptionService {

    private final InfoForAdoptionRepository repository;
   @Override
    public InfoForAdoption findInfoForAdoptionByTypeAnimal(TypeAnimal typeAnimal) {
                return repository.findInfoForAdoptionByTypeOfAnimal(typeAnimal).orElseThrow();
    }
}
