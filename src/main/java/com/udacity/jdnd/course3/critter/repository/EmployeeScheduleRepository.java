package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.EmployeeEntity;
import com.udacity.jdnd.course3.critter.entity.EmployeeScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeScheduleRepository extends JpaRepository<EmployeeScheduleEntity, Long> {

    List<EmployeeScheduleEntity> findByEmployee(EmployeeEntity employee);
}
