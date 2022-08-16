package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.EmployeeEntity;
import com.udacity.jdnd.course3.critter.entity.SkillEntity;
import com.udacity.jdnd.course3.critter.repository.SkillRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SkillService {

    private final SkillRepository repository;


    public SkillService(SkillRepository repository) {
        this.repository = repository;
    }

    public Set<SkillEntity> saveEmployeeSkill(EmployeeEntity employee, Set<EmployeeSkill> skills) {

        Set<SkillEntity> employeeSkills = new HashSet<>();
        if (!skills.isEmpty()) {
            for (EmployeeSkill skill : skills) {
                employeeSkills.add(new SkillEntity(skill, employee));
            }
            employeeSkills = new HashSet<>(this.repository.saveAll(employeeSkills));
        }

        return employeeSkills;
    }

    public Set<EmployeeEntity> findEmployeeBySkillIn(Set<EmployeeSkill> skills) {

        Set<EmployeeEntity> employees = new HashSet<>();
        for (EmployeeSkill skill : skills) {
            Set<SkillEntity> employeeSkills = this.repository.findBySkill(skill);
            if (employees.isEmpty()) {
                employees.addAll(employeeSkills.stream().map(SkillEntity::getEmployee).collect(Collectors.toSet()));
            } else {
                Set<EmployeeEntity> newEmployees = employeeSkills.stream().map(SkillEntity::getEmployee).collect(Collectors.toSet());
                employees = employees.stream().filter(newEmployees::contains).collect(Collectors.toSet());
            }
        }

        return employees;
    }
}
