package com.udacity.jdnd.course3.critter.entity;

import com.udacity.jdnd.course3.critter.pet.PetType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "pets")
public class PetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private PetType type;

    private String name;

    private LocalDate birthDate;

    private String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private CustomerEntity owner;

    @OneToMany(mappedBy = "pet", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ScheduleDetailEntity> petSchedules;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PetType getType() {
        return type;
    }

    public void setType(PetType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public CustomerEntity getOwner() {
        return owner;
    }

    public void setOwner(CustomerEntity owner) {
        this.owner = owner;
    }

    public List<ScheduleDetailEntity> getPetSchedules() {
        return petSchedules;
    }

    public void setPetSchedules(List<ScheduleDetailEntity> petSchedules) {
        this.petSchedules = petSchedules;
    }
}
