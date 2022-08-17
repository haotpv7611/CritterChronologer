package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.DayAvailableEntity;
import com.udacity.jdnd.course3.critter.entity.EmployeeEntity;
import com.udacity.jdnd.course3.critter.repository.DayAvailableRepository;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class DayAvailableService {

    private final DayAvailableRepository repository;

    public DayAvailableService(DayAvailableRepository repository) {
        this.repository = repository;
    }

    public Set<DayAvailableEntity> saveDaysAvailable(EmployeeEntity employee, Set<DayOfWeek> daysAvailable) {

        Set<DayAvailableEntity> days = new HashSet<>();
        if (daysAvailable != null && !daysAvailable.isEmpty()) {
            for (DayOfWeek day : daysAvailable) {
                days.add(new DayAvailableEntity(employee, day));
            }
            days = new HashSet<>(this.repository.saveAll(days));
        }

        return days;
    }

    public Set<EmployeeEntity> findEmployeeByDayOfWeek(LocalDate date) {

        DayOfWeek day = date.getDayOfWeek();
        Set<DayAvailableEntity> daysAvailable = this.repository.findByDayOfWeek(day);

        return daysAvailable.stream().map(DayAvailableEntity::getEmployee).collect(Collectors.toSet());
    }
}
