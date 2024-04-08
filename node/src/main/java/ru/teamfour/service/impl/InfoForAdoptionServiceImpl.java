package ru.teamfour.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.teamfour.dao.entity.infoForAdoption.InfoForAdoption;
import ru.teamfour.repositories.InfoForAdoptionRepository;
import ru.teamfour.service.api.infoForAdoption.InfoForAdoptionService;
@Service
@AllArgsConstructor
public class InfoForAdoptionServiceImpl implements InfoForAdoptionService {


    InfoForAdoptionRepository repository;
    @Override
    public InfoForAdoption findInfoForAdoptionById(Integer id) {
                return repository.findInfoForAdoptionById(id).get();
    }
}
