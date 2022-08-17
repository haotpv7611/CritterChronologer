package com.udacity.jdnd.course3.critter.service;

import com.udacity.jdnd.course3.critter.entity.ScheduleActivityEntity;
import com.udacity.jdnd.course3.critter.entity.ScheduleEntity;
import com.udacity.jdnd.course3.critter.repository.ScheduleActivityRepository;
import com.udacity.jdnd.course3.critter.user.EmployeeSkill;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class ScheduleActivityService {

    private final ScheduleActivityRepository repository;

    public ScheduleActivityService(ScheduleActivityRepository repository) {
        this.repository = repository;
    }

    public List<ScheduleActivityEntity> saveAllActivities(ScheduleEntity schedule, Set<EmployeeSkill> activities) {
        List<ScheduleActivityEntity> scheduleActivities = new ArrayList<>();
        if (activities != null && !activities.isEmpty()) {
            for (EmployeeSkill skill : activities) {
                ScheduleActivityEntity activity = new ScheduleActivityEntity(skill, schedule);
                scheduleActivities.add(activity);
            }
            scheduleActivities = this.repository.saveAll(scheduleActivities);
        }

        return scheduleActivities;
    }
}
