package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.PetEntity;
import com.udacity.jdnd.course3.critter.entity.PetScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetScheduleRepository extends JpaRepository<PetScheduleEntity, Long> {

    List<PetScheduleEntity> findByPet(PetEntity pet);
}
