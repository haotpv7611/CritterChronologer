package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.*;
import com.udacity.jdnd.course3.critter.repository.CustomerRepository;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.repository.PetRepository;
import com.udacity.jdnd.course3.critter.repository.ScheduleRepository;
import com.udacity.jdnd.course3.critter.schedule.ScheduleDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
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

    public ScheduleDTO createSchedule(ScheduleDTO scheduleDTO) {

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

        this.convertToDTO(schedule, scheduleDTO);

        return scheduleDTO;
    }

    public List<ScheduleDTO> getAllSchedules() {

        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        List<ScheduleEntity> schedules = this.repository.findAll();
        if (!schedules.isEmpty()) {
            for (ScheduleEntity schedule : schedules) {
                ScheduleDTO dto = new ScheduleDTO();
                this.convertToDTO(schedule, dto);
                scheduleDTOs.add(dto);
            }
        }

        return scheduleDTOs;
    }

    public List<ScheduleDTO> getScheduleForPet(Long petId) {

        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        PetEntity pet = this.petRepository.findById(petId)
                .orElseThrow(() -> new RuntimeException("Pet not found"));
        List<ScheduleEntity> schedules = this.petScheduleService.findScheduleByPet(pet);

        if (!schedules.isEmpty()) {
            for (ScheduleEntity schedule : schedules) {
                ScheduleDTO dto = new ScheduleDTO();
                this.convertToDTO(schedule, dto);
                scheduleDTOs.add(dto);
            }
        }

        return scheduleDTOs;
    }

    public List<ScheduleDTO> getScheduleForEmployee(Long employeeId) {

        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        EmployeeEntity employee = this.employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        List<ScheduleEntity> schedules = this.employeeScheduleService.findScheduleByEmployee(employee);

        if (!schedules.isEmpty()) {
            for (ScheduleEntity schedule : schedules) {
                ScheduleDTO dto = new ScheduleDTO();
                this.convertToDTO(schedule, dto);
                scheduleDTOs.add(dto);
            }
        }

        return scheduleDTOs;
    }

    public List<ScheduleDTO> getScheduleForCustomer(Long customerId) {

        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        CustomerEntity customer = this.customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        List<PetEntity> pets = customer.getPets();

        List<ScheduleEntity> schedules = new ArrayList<>();
        if (!pets.isEmpty()) {
            for (PetEntity pet : pets) {
                schedules.addAll(this.petScheduleService.findScheduleByPet(pet));
            }
        }

        if (!schedules.isEmpty()) {
            for (ScheduleEntity schedule : schedules) {
                ScheduleDTO dto = new ScheduleDTO();
                this.convertToDTO(schedule, dto);
                scheduleDTOs.add(dto);
            }
        }

        return scheduleDTOs;
    }

    private void convertToDTO(ScheduleEntity source, ScheduleDTO target) {

        target.setId(source.getId());
        target.setDate(source.getEventDate());

        if (!source.getActivities().isEmpty()) {
            Set<EmployeeSkill> activities = source.getActivities().stream()
                    .map(ScheduleActivityEntity::getActivity)
                    .collect(Collectors.toSet());
            target.setActivities(activities);
        }

        if (!source.getPetSchedules().isEmpty()) {
            List<Long> ids = source.getPetSchedules().stream()
                    .map(PetScheduleEntity::getPet)
                    .map(PetEntity::getId)
                    .collect(Collectors.toList());
            target.setPetIds(ids);
        }

        if (!source.getEmployeeSchedules().isEmpty()) {
            List<Long> ids = source.getEmployeeSchedules().stream()
                    .map(EmployeeScheduleEntity::getEmployee)
                    .map(EmployeeEntity::getId)
                    .collect(Collectors.toList());
            target.setEmployeeIds(ids);
        }
    }
}
