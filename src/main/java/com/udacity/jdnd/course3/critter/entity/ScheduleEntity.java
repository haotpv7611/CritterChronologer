package com.udacity.jdnd.course3.critter.entity;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "schedules")
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate eventDate;

    private DayOfWeek dayOfWeek;

    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ScheduleActivityEntity> activities;

    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<PetScheduleEntity> petSchedules;

    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<EmployeeScheduleEntity> employeeSchedules;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public List<ScheduleActivityEntity> getActivities() {
        return activities;
    }

    public void setActivities(List<ScheduleActivityEntity> activities) {
        this.activities = activities;
    }

    public List<PetScheduleEntity> getPetSchedules() {
        return petSchedules;
    }

    public void setPetSchedules(List<PetScheduleEntity> petSchedules) {
        this.petSchedules = petSchedules;
    }

    public List<EmployeeScheduleEntity> getEmployeeSchedules() {
        return employeeSchedules;
    }

    public void setEmployeeSchedules(List<EmployeeScheduleEntity> employeeSchedules) {
        this.employeeSchedules = employeeSchedules;
    }
}
