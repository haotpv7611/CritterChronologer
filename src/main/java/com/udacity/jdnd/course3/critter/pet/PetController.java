package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.service.PetService;
import org.springframework.web.bind.annotation.*;

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

        return this.petService.savePet(petDTO);
    }

    @GetMapping("/{petId}")
    public PetDTO getPet(@PathVariable long petId) {

        return this.petService.getPetById(petId);
    }

    @GetMapping
    public List<PetDTO> getPets(){
        throw new UnsupportedOperationException();
    }

    @GetMapping("/owner/{ownerId}")
    public List<PetDTO> getPetsByOwner(@PathVariable long ownerId) {

        return this.petService.getAllPetsByOwner(ownerId);
    }
}
