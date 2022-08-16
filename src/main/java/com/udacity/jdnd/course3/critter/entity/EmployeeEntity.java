package com.udacity.jdnd.course3.critter.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "employees")
public class EmployeeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<SkillEntity> skills;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<DayAvailableEntity> daysAvailable;

    @OneToMany(mappedBy = "employee", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ScheduleDetailEntity> employeeSchedules;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<SkillEntity> getSkills() {
        return skills;
    }

    public void setSkills(Set<SkillEntity> skills) {
        this.skills = skills;
    }

    public Set<DayAvailableEntity> getDaysAvailable() {
        return daysAvailable;
    }

    public void setDaysAvailable(Set<DayAvailableEntity> daysAvailable) {
        this.daysAvailable = daysAvailable;
    }

    public List<ScheduleDetailEntity> getEmployeeSchedules() {
        return employeeSchedules;
    }

    public void setEmployeeSchedules(List<ScheduleDetailEntity> employeeSchedules) {
        this.employeeSchedules = employeeSchedules;
    }
}
