package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.*;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ScheduleService {

    private final ScheduleRepository repository;
    private final PetRepository petRepository;
    private final EmployeeRepository employeeRepository;
    private final CustomerRepository customerRepository;
    private final ScheduleActivityService scheduleActivityService;
    private final PetScheduleService petScheduleService;
    private final EmployeeScheduleService employeeScheduleService;

    public ScheduleService(ScheduleRepository repository,
                           PetRepository petRepository,
                           EmployeeRepository employeeRepository,
                           CustomerRepository customerRepository,
                           ScheduleActivityService scheduleActivityService,
                           PetScheduleService petScheduleService,
                           EmployeeScheduleService employeeScheduleService) {
        this.repository = repository;
        this.petRepository = petRepository;
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
        this.scheduleActivityService = scheduleActivityService;
        this.petScheduleService = petScheduleService;
        this.employeeScheduleService = employeeScheduleService;
    }

    public ScheduleEntity createSchedule(ScheduleDTO scheduleDTO) {

        ScheduleEntity schedule = new ScheduleEntity();
        schedule.setEventDate(scheduleDTO.getDate());
        schedule.setDayOfWeek(scheduleDTO.getDate().getDayOfWeek());
        schedule = this.repository.save(schedule);

        List<ScheduleActivityEntity> activities = this.scheduleActivityService.saveAllActivities(schedule, scheduleDTO.getActivities());
        schedule.setActivities(activities);

        List<PetScheduleEntity> petSchedules = this.petScheduleService.saveAllPetSchedules(schedule, scheduleDTO.getPetIds());
        schedule.setPetSchedules(petSchedules);

        List<EmployeeScheduleEntity> employeeSchedules = this.employeeScheduleService.saveAllEmployeeSchedules(schedule, scheduleDTO.getEmployeeIds());
        schedule.setEmployeeSchedules(employeeSchedules);

        return schedule;
    }

    public List<ScheduleEntity> getAllSchedules() {

        return this.repository.findAll();
    }

    public List<ScheduleEntity> getScheduleForPet(Long petId) {
        PetEntity pet = this.petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));

        return this.petScheduleService.findScheduleByPet(pet);
    }

    public List<ScheduleEntity> getScheduleForEmployee(Long employeeId) {

        EmployeeEntity employee = this.employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        return this.employeeScheduleService.findScheduleByEmployee(employee);
    }

    public List<ScheduleEntity> getScheduleForCustomer(Long customerId) {

        List<ScheduleEntity> schedules = new ArrayList<>();
        CustomerEntity customer = this.customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        List<PetEntity> pets = customer.getPets();
        if (!pets.isEmpty()) {
            for (PetEntity pet : pets) {
                schedules.addAll(this.petScheduleService.findScheduleByPet(pet));
            }
        }

        return schedules;
    }
}
