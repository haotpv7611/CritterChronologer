package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.EmployeeEntity;
import com.udacity.jdnd.course3.critter.entity.SkillEntity;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;
    private final SkillService skillService;
    private final DayAvailableService dayAvailableService;

    public EmployeeService(EmployeeRepository repository,
                           SkillService skillService,
                           DayAvailableService dayAvailableService) {
        this.repository = repository;
        this.skillService = skillService;
        this.dayAvailableService = dayAvailableService;
    }

    public EmployeeDTO saveEmployee(EmployeeDTO dto) {

        EmployeeEntity employee = new EmployeeEntity();
        employee.setName(dto.getName());
        employee = this.repository.save(employee);

        if (dto.getSkills() != null) {
            Set<SkillEntity> skills = this.skillService.saveEmployeeSkill(employee, dto.getSkills());
            employee.setSkills(skills);
        }
//        if (dto.getDaysAvailable() != null) {
//            Set<DayOfWeekEntity> days = new HashSet<>();
//            for (DayOfWeek day : dto.getDaysAvailable()) {
//                DayOfWeekEntity dayOfWeekEntity = this.dayAvailableService.saveDayAvailable(day, employee);
//                days.add(dayOfWeekEntity);
//            }
//            employee.setDaysAvailable(days);
//        }

        this.convertToDTO(employee, dto);

        return dto;
    }

    public EmployeeDTO getEmployeeById(Long id) {

        EmployeeEntity employee = this.repository.findById(id).orElseThrow(RuntimeException::new);
        EmployeeDTO dto = new EmployeeDTO();
        this.convertToDTO(employee, dto);

        return dto;
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, Long employeeId) {

        EmployeeEntity employee = this.repository.findById(employeeId).orElseThrow(RuntimeException::new);
//        if (daysAvailable != null) {
//            Set<DayOfWeekEntity> days = new HashSet<>();
//            for (DayOfWeek day : daysAvailable) {
//                DayOfWeekEntity dayOfWeekEntity = this.dayAvailableService.saveDayAvailable(day, employee);
//                days.add(dayOfWeekEntity);
//            }
//            employee.setDaysAvailable(days);
//        }
    }

    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeDTO) {
//        Set<SkillEntity> skills = this.skillService.findBySkillIn(employeeDTO.getSkills());
//        Set<EmployeeEntity> employeeSkills = skills.stream().map(SkillEntity::getEmployee).collect(Collectors.toSet());
//
//        Set<DayOfWeek> dayOfWeeks = new HashSet<>();
//        dayOfWeeks.add(employeeDTO.getDate().getDayOfWeek());
//        Set<DayOfWeekEntity> days = this.dayAvailableService.findByDayOfWeekIn(dayOfWeeks);
//        Set<EmployeeEntity> employeeDays = days.stream().map(DayOfWeekEntity::getEmployee).collect(Collectors.toSet());
//
//        Set<EmployeeEntity> employees = employeeSkills.stream().filter(employeeDays::contains).collect(Collectors.toSet());
        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
//        if (!employees.isEmpty()) {
//            for (EmployeeEntity employee : employees) {
//                EmployeeDTO dto = new EmployeeDTO();
//                this.convertToDTO(employee, dto);
//                employeeDTOs.add(dto);
//            }
//        }

        return employeeDTOs;
    }

    private void convertToDTO(EmployeeEntity source, EmployeeDTO target) {
        BeanUtils.copyProperties(source, target);
        Set<SkillEntity> skills = source.getSkills();
        if (skills != null) {
            Set<EmployeeSkill> employeeSkills = skills.stream().map(SkillEntity::getSkill).collect(Collectors.toSet());
            target.setSkills(employeeSkills);
        }
//
//        Set<DayOfWeekEntity> daysAvailable = source.getDaysAvailable();
//        if (daysAvailable != null) {
//            Set<DayOfWeek> dayOfWeeks = daysAvailable.stream().map(DayOfWeekEntity::getDayOfWeek).collect(Collectors.toSet());
//            target.setDaysAvailable(dayOfWeeks);
//        }
    }
}
