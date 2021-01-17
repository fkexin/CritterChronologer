package com.udacity.jdnd.course3.critter.pet;

import com.udacity.jdnd.course3.critter.schedule.Schedule;
import com.udacity.jdnd.course3.critter.user.Customer;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class Pet {
    @Id
    @GeneratedValue
    private Long id;

    private PetType type;
    private String name;

    @ManyToOne //many pets can belong to one owner
    @JoinColumn(name = "owner_id") // map the join column in the pet table
    private Customer owner;

    private LocalDate birthDate;
    private String notes;

    @ManyToMany
    private List<Schedule> schedules;

    public Pet() {
    }

    public Pet(PetType type, String name, Customer owner, LocalDate birthDate) {
        this.type = type;
        this.name = name;
        this.owner = owner;
        this.birthDate = birthDate;
    }


    public Pet(PetType type, String name, Customer owner, LocalDate birthDate, String notes, List<Schedule> schedules) {
        this.type = type;
        this.name = name;
        this.owner = owner;
        this.birthDate = birthDate;
        this.notes = notes;
        this.schedules = schedules;
    }

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

    public Customer getOwner() {
        return owner;
    }

    public void setOwner(Customer owner) {
        this.owner = owner;
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


    public List<Schedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<Schedule> schedules) {
        this.schedules = schedules;
    }
}

