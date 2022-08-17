package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.CustomerEntity;
import com.udacity.jdnd.course3.critter.entity.PetEntity;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository repository;
    private final PetRepository petRepository;

    public CustomerService(CustomerRepository repository,
                           PetRepository petRepository) {
        this.repository = repository;
        this.petRepository = petRepository;
    }

    public CustomerDTO saveCustomer(CustomerDTO dto) {

        CustomerEntity customer = new CustomerEntity();
        BeanUtils.copyProperties(dto, customer);
        customer = this.repository.save(customer);
        this.convertToDTO(customer, dto);

        return dto;
    }

    public List<CustomerDTO> getAllCustomer() {

        List<CustomerDTO> customerDTOs = new ArrayList<>();
        List<CustomerEntity> customers = this.repository.findAll();
        if (!customers.isEmpty()) {
            for (CustomerEntity customer : customers) {
                CustomerDTO dto = new CustomerDTO();
                this.convertToDTO(customer, dto);
                customerDTOs.add(dto);
            }
        }

        return customerDTOs;
    }

    public CustomerDTO findOwnerByPetId(Long petId) {

        PetEntity pet = this.petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
        CustomerEntity customer = this.repository.findById(pet.getOwner().getId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        CustomerDTO dto = new CustomerDTO();
        this.convertToDTO(customer, dto);

        return dto;
    }

    private void convertToDTO(CustomerEntity source, CustomerDTO target) {

        BeanUtils.copyProperties(source, target);
        List<PetEntity> pets = source.getPets();
        if (pets != null) {
            List<Long> ids = pets.stream()
                    .map(PetEntity::getId)
                    .collect(Collectors.toList());
            target.setPetIds(ids);
        }
    }
}
