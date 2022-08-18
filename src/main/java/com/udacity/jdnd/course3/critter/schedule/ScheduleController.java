package com.udacity.jdnd.course3.critter.schedule;

import com.udacity.jdnd.course3.critter.entity.*;
import com.udacity.jdnd.course3.critter.service.ScheduleService;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Handles web requests related to Schedules.
 */
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @PostMapping
    public ScheduleDTO createSchedule(@RequestBody ScheduleDTO scheduleDTO) {

        ScheduleEntity schedule = this.scheduleService.createSchedule(scheduleDTO);
        this.convertToDTO(schedule, scheduleDTO);

        return scheduleDTO;
    }

    @GetMapping
    public List<ScheduleDTO> getAllSchedules() {

        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        List<ScheduleEntity> schedules = this.scheduleService.getAllSchedules();
        if (!schedules.isEmpty()) {
            for (ScheduleEntity schedule : schedules) {
                ScheduleDTO dto = new ScheduleDTO();
                this.convertToDTO(schedule, dto);
                scheduleDTOs.add(dto);
            }
        }

        return scheduleDTOs;
    }

    @GetMapping("/pet/{petId}")
    public List<ScheduleDTO> getScheduleForPet(@PathVariable long petId) {

        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        List<ScheduleEntity> schedules = this.scheduleService.getScheduleForPet(petId);
        if (!schedules.isEmpty()) {
            for (ScheduleEntity schedule : schedules) {
                ScheduleDTO dto = new ScheduleDTO();
                this.convertToDTO(schedule, dto);
                scheduleDTOs.add(dto);
            }
        }

        return scheduleDTOs;
    }

    @GetMapping("/employee/{employeeId}")
    public List<ScheduleDTO> getScheduleForEmployee(@PathVariable long employeeId) {

        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        List<ScheduleEntity> schedules = this.scheduleService.getScheduleForEmployee(employeeId);
        if (!schedules.isEmpty()) {
            for (ScheduleEntity schedule : schedules) {
                ScheduleDTO dto = new ScheduleDTO();
                this.convertToDTO(schedule, dto);
                scheduleDTOs.add(dto);
            }
        }

        return scheduleDTOs;
    }

    @GetMapping("/customer/{customerId}")
    public List<ScheduleDTO> getScheduleForCustomer(@PathVariable long customerId) {

        List<ScheduleDTO> scheduleDTOs = new ArrayList<>();
        List<ScheduleEntity> schedules = this.scheduleService.getScheduleForCustomer(customerId);
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
