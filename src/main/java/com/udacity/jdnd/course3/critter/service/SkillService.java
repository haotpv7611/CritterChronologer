package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.EmployeeEntity;
import com.udacity.jdnd.course3.critter.entity.SkillEntity;
import com.udacity.jdnd.course3.critter.repository.SkillRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class SkillService {

    private final SkillRepository repository;


    public SkillService(SkillRepository repository) {
        this.repository = repository;
    }

//    public SkillEntity saveEmployeeSkill(EmployeeSkill skill, EmployeeEntity employee) {
//        SkillEntity employeeSkill = new SkillEntity(skill, employee);
//
//        return this.repository.save(employeeSkill);
//    }

    public Set<SkillEntity> findBySkillIn(Set<EmployeeSkill> skills) {

        return this.repository.findBySkillIn(skills);
    }

//    private void convertToDTO(SkillEntity source, SkillDTO target) {
//        BeanUtils.copyProperties(source, target);
//        target.setEmployeeId(source.getId());
//    }
}
