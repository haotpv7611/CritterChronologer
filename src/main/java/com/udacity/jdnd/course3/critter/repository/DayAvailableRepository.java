package com.udacity.jdnd.course3.critter.repository;

import com.udacity.jdnd.course3.critter.entity.DayAvailableEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.Set;

@Repository
public interface DayAvailableRepository extends JpaRepository<DayAvailableEntity, Long> {

    Set<DayAvailableEntity> findByDayOfWeek(DayOfWeek day);
}
