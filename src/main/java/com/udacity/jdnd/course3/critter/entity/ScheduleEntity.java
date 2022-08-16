package com.udacity.jdnd.course3.critter.entity;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "schedules")
public class ScheduleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date eventDate;

    private String activities;

    @OneToMany(mappedBy = "schedule", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ScheduleDetailEntity> scheduleDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getEventDate() {
        return eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public List<ScheduleDetailEntity> getScheduleDetails() {
        return scheduleDetails;
    }

    public void setScheduleDetails(List<ScheduleDetailEntity> scheduleDetails) {
        this.scheduleDetails = scheduleDetails;
    }
}
