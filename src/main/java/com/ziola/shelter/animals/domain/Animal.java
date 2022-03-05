package com.ziola.shelter.animals.domain;


import com.ziola.shelter.aws.domain.Image;
import com.ziola.shelter.workers.domain.Worker;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "animal")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animalId")
    private Integer id;

    @Size(min = 2, max = 20, message = "{animal.domain.size.error}")
    @Column(name = "animalsName")
    private String name;


    @Column(name = "animalsAge")
    @NotNull(message = "{animal.domain.null.error}" )
    private int age;

    @NotNull(message = "{animal.domain.null.error}" )
    @Column(name = "animalsSpecie")
    private String specie;

    @Size(max = 450)
    @NotNull(message = "{animal.domain.null.error}" )
    @Column(name = "animalsDescription")
    private String description;

    @Size(max = 150)
    @Column(name = "animalsHealth")
    @NotNull(message = "{animal.domain.null.error}" )
    private String health;

    @Size(max = 45)
    @Column(name = "animalsRace")
    @NotNull(message = "{animal.domain.null.error}" )
    private String race;

    @Size(max = 45)
    @Column(name = "animalsSex")
    @NotNull(message = "{animal.domain.null.error}" )
    private String sex;

    @Size(max = 20)
    @Column(name = "animalsCity")
    @NotNull(message = "{animal.domain.null.error}" )
    private String city;

    @ManyToOne
    @JoinColumn(name = "workers_id", nullable = false)
    private Worker worker;

    @OneToOne(targetEntity = Image.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id", nullable = false)
    private Image image;

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Animal() {
    }

    public Animal(@Size(min = 2, max = 20, message = "{animal.domain.size.error}") String name, @NotNull(message = "{animal.domain.null.error}") int age,
                  @NotNull(message = "{animal.domain.null.error}") String specie, @Size(max = 450) @NotNull(message = "{animal.domain.null.error}") String description,
                  @Size(max = 150) @NotNull(message = "{animal.domain.null.error}") String health, @Size(max = 45) @NotNull(message = "{animal.domain.null.error}") String race,
                  @Size(max = 45) @NotNull(message = "{animal.domain.null.error}") String sex, @Size(max = 20) @NotNull(message = "{animal.domain.null.error}") String city,
                  Worker worker) {
        this.name = name;
        this.age = age;
        this.specie = specie;
        this.description = description;
        this.health = health;
        this.race = race;
        this.sex = sex;
        this.city = city;
        this.worker = worker;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSpecie() {
        return specie;
    }

    public void setSpecie(String specie) {
        this.specie = specie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHealth() {
        return health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}