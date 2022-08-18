package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.*;
import com.udacity.jdnd.course3.critter.repository.EmployeeScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
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
        List<EmployeeEntity> employees = this.employeeService.findAllEmployeesByIds(employeeIds);
        if (!employees.isEmpty()) {
            for (EmployeeEntity employee : employees) {
                employeeSchedules.add(new EmployeeScheduleEntity(employee, schedule));
            }
            employeeSchedules = this.repository.saveAll(employeeSchedules);
        }

        return employeeSchedules;
    }

    public List<ScheduleEntity> findScheduleByEmployee(EmployeeEntity employee) {

        List<ScheduleEntity> schedules = new ArrayList<>();
        List<EmployeeScheduleEntity> employeeSchedules = this.repository.findByEmployee(employee);
        if (!employeeSchedules.isEmpty()) {
            for (EmployeeScheduleEntity employeeSchedule : employeeSchedules) {
                schedules.add(employeeSchedule.getSchedule());
            }
        }

        return schedules;
    }
}
