package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.DayAvailableEntity;
import com.udacity.jdnd.course3.critter.entity.EmployeeEntity;
import com.udacity.jdnd.course3.critter.entity.SkillEntity;
import com.udacity.jdnd.course3.critter.repository.EmployeeRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeRequestDTO;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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

        if (dto.getSkills() != null && !dto.getSkills().isEmpty()) {
            Set<SkillEntity> skills = this.skillService.saveEmployeeSkill(employee, dto.getSkills());
            employee.setSkills(skills);
        }
        if (dto.getDaysAvailable() != null && !dto.getDaysAvailable().isEmpty()) {
            Set<DayAvailableEntity> days = this.dayAvailableService.saveDaysAvailable(employee, dto.getDaysAvailable());
            employee.setDaysAvailable(days);
        }
        this.convertToDTO(employee, dto);

        return dto;
    }

    public EmployeeDTO getEmployeeById(Long id) {

        EmployeeEntity employee = this.repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        EmployeeDTO dto = new EmployeeDTO();
        this.convertToDTO(employee, dto);

        return dto;
    }

    public void setAvailability(Set<DayOfWeek> daysAvailable, Long employeeId) {

        EmployeeEntity employee = this.repository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        if (daysAvailable != null && !daysAvailable.isEmpty()) {
            Set<DayAvailableEntity> days = this.dayAvailableService.saveDaysAvailable(employee, daysAvailable);
            employee.setDaysAvailable(days);
        }
    }

    public List<EmployeeDTO> findEmployeesForService(EmployeeRequestDTO employeeDTO) {

        Set<EmployeeEntity> employeeSkills = this.skillService.findEmployeeBySkillIn(employeeDTO.getSkills());
        Set<EmployeeEntity> employees = this.dayAvailableService.findEmployeeByDayOfWeek(employeeDTO.getDate());
        if (!employeeSkills.isEmpty() && !employees.isEmpty()) {
            employees = employeeSkills.stream()
                    .filter(employees::contains)
                    .collect(Collectors.toSet());
        }

        List<EmployeeDTO> employeeDTOs = new ArrayList<>();
        if (!employees.isEmpty()) {
            for (EmployeeEntity employee : employees) {
                EmployeeDTO dto = new EmployeeDTO();
                this.convertToDTO(employee, dto);
                employeeDTOs.add(dto);
            }
        }

        return employeeDTOs;
    }

    public List<EmployeeEntity> findAllEmployeesByIds(List<Long> ids) {

        return this.repository.findAllById(ids);
    }

    private void convertToDTO(EmployeeEntity source, EmployeeDTO target) {

        BeanUtils.copyProperties(source, target);
        Set<SkillEntity> skills = source.getSkills();
        if (skills != null) {
            Set<EmployeeSkill> employeeSkills = skills.stream()
                    .map(SkillEntity::getSkill)
                    .collect(Collectors.toSet());
            target.setSkills(employeeSkills);
        }

        Set<DayAvailableEntity> daysAvailable = source.getDaysAvailable();
        if (daysAvailable != null) {
            Set<DayOfWeek> dayOfWeeks = daysAvailable.stream()
                    .map(DayAvailableEntity::getDayOfWeek)
                    .collect(Collectors.toSet());
            target.setDaysAvailable(dayOfWeeks);
        }
    }
}
