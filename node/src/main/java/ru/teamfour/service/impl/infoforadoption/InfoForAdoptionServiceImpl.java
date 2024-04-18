package ru.teamfour.service.impl.infoforadoption;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ru.teamfour.dao.entity.animal.TypeAnimal;
import ru.teamfour.dao.entity.infoforadoption.InfoForAdoption;
import ru.teamfour.repositories.InfoForAdoptionRepository;
import ru.teamfour.service.api.infoforadoption.InfoForAdoptionService;

@Service
@AllArgsConstructor
@Validated
public class InfoForAdoptionServiceImpl implements InfoForAdoptionService {

    private final InfoForAdoptionRepository repository;

    @Override
    public InfoForAdoption findInfoForAdoptionByTypeAnimal(@NonNull TypeAnimal typeAnimal) {
        return repository.findInfoForAdoptionByTypeOfAnimal(typeAnimal).orElseThrow(IllegalArgumentException::new);
    }

}
