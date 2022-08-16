package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.CustomerEntity;
import com.udacity.jdnd.course3.critter.entity.PetEntity;
import com.udacity.jdnd.course3.critter.pet.PetDTO;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository repository;
    private final PetService petService;

    public CustomerService(CustomerRepository repository,
                           PetService petService) {
        this.repository = repository;
        this.petService = petService;
    }

    public CustomerDTO saveCustomer(CustomerDTO dto) {

        CustomerEntity customer = new CustomerEntity();
        BeanUtils.copyProperties(dto, customer);
        customer = this.repository.save(customer);
        this.convertToDTO(customer, dto);

        return dto;
    }

    public List<CustomerDTO> getAllCustomer() {

        List<CustomerEntity> customers = this.repository.findAll();
        List<CustomerDTO> customerDTOs = new ArrayList<>();
        for (CustomerEntity customer : customers) {
            CustomerDTO dto = new CustomerDTO();
            this.convertToDTO(customer, dto);
            customerDTOs.add(dto);
        }

        return customerDTOs;
    }

    public CustomerDTO findOwnerByPetId(Long petId) {

        PetDTO pet = this.petService.getPetById(petId);
        CustomerEntity customer = this.repository.findById(pet.getOwnerId()).orElseThrow(RuntimeException::new);
        CustomerDTO dto = new CustomerDTO();
        this.convertToDTO(customer, dto);

        return dto;
    }

    private void convertToDTO(CustomerEntity source, CustomerDTO target) {

        BeanUtils.copyProperties(source, target);
        List<PetEntity> pets = source.getPets();
        if (pets != null) {
            List<Long> ids = pets.stream().map(PetEntity::getId).collect(Collectors.toList());
            target.setPetIds(ids);
        }
    }
}
