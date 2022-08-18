package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.CustomerEntity;
import com.udacity.jdnd.course3.critter.entity.PetEntity;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PetService {

    private final PetRepository petRepository;
    private final CustomerRepository customerRepository;

    public PetService(PetRepository petRepository,
                      CustomerRepository customerRepository) {
        this.petRepository = petRepository;
        this.customerRepository = customerRepository;
    }

    public PetEntity savePet(PetDTO dto) {

        PetEntity pet = new PetEntity();
        BeanUtils.copyProperties(dto, pet);

        CustomerEntity owner = this.customerRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        pet.setOwner(owner);
        pet.setType(dto.getType());
        pet = this.petRepository.save(pet);

        List<PetEntity> pets = owner.getPets();
        if (pets == null) {
            pets = new ArrayList<>();
        }
        pets.add(pet);
        owner.setPets(pets);

        return pet;
    }

    public PetEntity getPetById(Long id) {

        return this.petRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
    }

    public List<PetEntity> getAllPets() {

        return this.petRepository.findAll();
    }

    public List<PetEntity> getAllPetsByOwner(Long ownerId) {

        return this.petRepository.findByOwnerId(ownerId);
    }

    public List<PetEntity> findAllPetsById(List<Long> ids) {

        return this.petRepository.findAllById(ids);
    }
}
