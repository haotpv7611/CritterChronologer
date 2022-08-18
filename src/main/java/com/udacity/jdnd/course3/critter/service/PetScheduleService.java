package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.*;
import com.udacity.jdnd.course3.critter.repository.PetScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
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
                petSchedules.add(new PetScheduleEntity(pet, schedule));
            }
            petSchedules = this.repository.saveAll(petSchedules);
        }

        return petSchedules;
    }

    public List<ScheduleEntity> findScheduleByPet(PetEntity pet) {

        List<ScheduleEntity> schedules = new ArrayList<>();
        List<PetScheduleEntity> petSchedules = this.repository.findByPet(pet);
        if (!petSchedules.isEmpty()) {
            for (PetScheduleEntity petSchedule : petSchedules) {
                schedules.add(petSchedule.getSchedule());
            }
        }

        return schedules;
    }
}
