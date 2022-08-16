package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.dto.SkillDTO;
import com.udacity.jdnd.course3.critter.entity.DayAvailableEntity;
import com.udacity.jdnd.course3.critter.entity.EmployeeEntity;
import com.udacity.jdnd.course3.critter.entity.SkillEntity;
import com.udacity.jdnd.course3.critter.repository.DayAvailableRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.Set;

@Service
public class DayAvailableService {

    private final DayAvailableRepository repository;

    public DayAvailableService(DayAvailableRepository repository) {
        this.repository = repository;
    }

    public Set<DayAvailableEntity> saveDaysAvailable(EmployeeEntity employee, Set<DayOfWeek> daysAvailable) {

        Set<DayAvailableEntity> days = new HashSet<>();
        if (!daysAvailable.isEmpty()) {
            for (DayOfWeek day : daysAvailable) {
                days.add(new DayAvailableEntity(employee, day));
            }

            days = new HashSet<>(this.repository.saveAll(days));
        }

        return days;
    }

    public Set<DayAvailableEntity> findByDayOfWeekIn(Set<DayOfWeek> days) {

        return this.repository.findByDayOfWeekIn(days);
    }

    private void convertToDTO(SkillEntity source, SkillDTO target) {
        BeanUtils.copyProperties(source, target);
        target.setEmployeeId(source.getId());
    }
}
