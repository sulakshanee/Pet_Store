package com.example.petstore;

import javax.persistence.*;

@Entity
@Table(name = "pets")
public class Pets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private long petId;
    
    @Column(name = "pet_type")
    private String petType;

    @Column(name="pet_name")
    private String petName;

    @Column(name = "pet_age")
    private Integer petAge;

    public Pets() {
    }

    public Pets(String petType, String petName, Integer petAge) {
        this.petType = petType;
        this.petName = petName;
        this.petAge = petAge;
    }

    public long getPetId() {
        return petId;
    }

    public void setPetId(long petId) {
        this.petId = petId;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public Integer getPetAge() {
        return petAge;
    }

    public void setPetAge(Integer petAge) {
        this.petAge = petAge;
    }
}
