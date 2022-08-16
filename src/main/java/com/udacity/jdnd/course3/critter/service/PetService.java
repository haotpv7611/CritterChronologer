package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.CustomerEntity;
import com.udacity.jdnd.course3.critter.entity.PetEntity;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.pet.PetType;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class PetService {

    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    public PetService(PetRepository petRepository,
                      CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public PetDTO savePet(PetDTO dto) {

        PetEntity pet = new PetEntity();
        BeanUtils.copyProperties(dto, pet);

        CustomerEntity owner = this.customerRepository.findById(dto.getOwnerId()).orElseThrow(RuntimeException::new);
        pet.setOwner(owner);
        pet.setType(dto.getType());

        pet = this.petRepository.save(pet);
        List<PetEntity> pets = owner.getPets();
        if (pets == null) {
            pets = new ArrayList<>();
        }
        pets.add(pet);

        owner.setPets(pets);
        this.convertToDTO(pet, dto);

        return dto;
    }

    public PetDTO getPetById(Long id) {

        PetEntity pet = this.petRepository.findById(id).orElseThrow(RuntimeException::new);
        PetDTO dto = new PetDTO();
        this.convertToDTO(pet, dto);

        return dto;
    }

    public List<PetDTO> getAllPetsByOwner(Long ownerId) {

        List<PetEntity> pets = this.petRepository.findByOwnerId(ownerId);
        List<PetDTO> petDTOs = new ArrayList<>();

        if (pets != null) {
            for (PetEntity pet : pets) {
                PetDTO dto = new PetDTO();
                this.convertToDTO(pet, dto);
                petDTOs.add(dto);
            }
        }

        return petDTOs;
    }

    private void convertToDTO(PetEntity source, PetDTO target) {
        BeanUtils.copyProperties(source, target);
        target.setOwnerId(source.getOwner().getId());
    }
}