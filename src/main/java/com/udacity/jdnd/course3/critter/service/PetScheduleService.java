package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.PetEntity;
import com.udacity.jdnd.course3.critter.entity.PetScheduleEntity;
import com.udacity.jdnd.course3.critter.entity.ScheduleEntity;
import com.udacity.jdnd.course3.critter.repository.PetScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PetScheduleService {

    private final PetScheduleRepository repository;
    private final PetService petService;

    public PetScheduleService(PetScheduleRepository repository,
                              PetService petService) {
        this.repository = repository;
        this.petService = petService;
    }

    public List<PetScheduleEntity> saveAllPetSchedules(ScheduleEntity schedule, List<Long> petIds) {

        List<PetScheduleEntity> petSchedules = new ArrayList<>();
        List<PetEntity> pets = this.petService.findAllPetsById(petIds);
        if (!pets.isEmpty()) {
            for (PetEntity pet : pets) {
                PetScheduleEntity petSchedule = new PetScheduleEntity(pet, schedule);
                petSchedules.add(petSchedule);
            }
            petSchedules = this.repository.saveAll(petSchedules);
        }

        return petSchedules;
    }
}
