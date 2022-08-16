package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.EmployeeEntity;
import com.udacity.jdnd.course3.critter.entity.SkillEntity;
import com.udacity.jdnd.course3.critter.repository.SkillRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

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
        }

        return new HashSet<>(this.repository.saveAll(employeeSkills));
    }

    public Set<SkillEntity> findBySkillIn(Set<EmployeeSkill> skills) {

        return this.repository.findBySkillIn(skills);
    }

//    private void convertToDTO(SkillEntity source, SkillDTO target) {
//        BeanUtils.copyProperties(source, target);
//        target.setEmployeeId(source.getId());
//    }
}
