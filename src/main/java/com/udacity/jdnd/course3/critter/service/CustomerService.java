package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.CustomerEntity;
import com.udacity.jdnd.course3.critter.entity.PetEntity;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.user.CustomerDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CustomerService {

    private final CustomerRepository repository;
    private final PetRepository petRepository;

    public CustomerService(CustomerRepository repository,
                           PetRepository petRepository) {
        this.repository = repository;
        this.petRepository = petRepository;
    }

    public CustomerEntity saveCustomer(CustomerDTO dto) {

        CustomerEntity customer = new CustomerEntity();
        BeanUtils.copyProperties(dto, customer);

        return this.repository.save(customer);
    }

    public List<CustomerEntity> getAllCustomer() {

        return this.repository.findAll();
    }

    public CustomerEntity findOwnerByPetId(Long petId) {

        PetEntity pet = this.petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
        return this.repository.findById(pet.getOwner().getId())
                .orElseThrow(() -> new RuntimeException("Customer not found"));
    }
}
