package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.entity.PetEntity;
import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles web requests related to Pets.
 */
@RestController
@RequestMapping("/pet")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @PostMapping
    public PetDTO savePet(@RequestBody PetDTO petDTO) {

        PetEntity pet = this.petService.savePet(petDTO);
        this.convertToDTO(pet, petDTO);

        return petDTO;
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {

        PetEntity pet = this.petService.getPetById(petId);
        PetDTO petDTO = new PetDTO();
        this.convertToDTO(pet, petDTO);

        return petDTO;
    }

    @GetMapping
    public List<PetDTO> getPets(){

        List<PetDTO> petDTOs = new ArrayList<>();
        List<PetEntity> pets = this.petService.getAllPets();
        if (!pets.isEmpty()) {
            for (PetEntity pet : pets) {
                PetDTO dto = new PetDTO();
                this.convertToDTO(pet, dto);
                petDTOs.add(dto);
            }
        }

        return petDTOs;
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {

        List<PetDTO> petDTOs = new ArrayList<>();
        List<PetEntity> pets = this.petService.getAllPetsByOwner(ownerId);
        if (!pets.isEmpty()) {
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
