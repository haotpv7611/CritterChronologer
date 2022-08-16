package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.SkillEntity;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SkillRepository extends JpaRepository<SkillEntity, Long> {

    Set<SkillEntity> findBySkill(EmployeeSkill skill);
}
