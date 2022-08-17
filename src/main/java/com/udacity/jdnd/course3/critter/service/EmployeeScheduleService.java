package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.*;
import com.udacity.jdnd.course3.critter.repository.EmployeeScheduleRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeScheduleService {

    private final EmployeeScheduleRepository repository;
    private final EmployeeService employeeService;

    public EmployeeScheduleService(EmployeeScheduleRepository repository,
                                   EmployeeService employeeService) {
        this.repository = repository;
        this.employeeService = employeeService;
    }

    public List<EmployeeScheduleEntity> saveAllEmployeeSchedules(ScheduleEntity schedule, List<Long> employeeIds) {

        List<EmployeeScheduleEntity> employeeSchedules = new ArrayList<>();
        List<EmployeeEntity> employees = this.employeeService.findAllPetsById(employeeIds);
        if (!employees.isEmpty()) {
            for (EmployeeEntity employee : employees) {
                EmployeeScheduleEntity employeeSchedule = new EmployeeScheduleEntity(employee, schedule);
                employeeSchedules.add(employeeSchedule);
            }
            employeeSchedules = this.repository.saveAll(employeeSchedules);
        }

        return employeeSchedules;
    }
}
