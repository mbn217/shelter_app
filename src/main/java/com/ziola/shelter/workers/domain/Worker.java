package com.ziola.shelter.workers.domain;

import com.ziola.shelter.animals.domain.Animal;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Table(name = "worker")
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workersId")
    private Integer id;
    @Column(name = "workersEmail")
    @Email(message = "*Wprowadź poprawny adres")
    @NotEmpty(message = "*Wprowadź poprawny adres")
    private String email;
    @Column(name = "workersPassword")
    @Length(min = 5, message = "*Hasło musi mieć przynajmniej 5 znaków")
    @NotEmpty(message = "*Wprowadź hasło")
    private String password;
    @Column(name = "workersName")
    @NotEmpty(message = "*Wprowadź imię")
    private String name;
    @Column(name = "workersLastName")
    @NotEmpty(message = "*Wprowadź nazwisko")
    private String lastName;
    @Column(name = "workersActive")
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "worker")
    private List<Animal> animals;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }


    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Worker(@Email(message = "*Wprowadź poprawny adres") @NotEmpty(message = "*Wprowadź poprawny adres") String email, @Length(min = 5, message = "*Hasło musi mieć przynajmniej 5 znaków") @NotEmpty(message = "*Wprowadź hasło") String password, @NotEmpty(message = "*Wprowadź imię") String name, @NotEmpty(message = "*Wprowadź nazwisko") String lastName, boolean active, Role role, List<Animal> animals) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.lastName = lastName;
        this.active = active;
        this.role = role;
        this.animals = animals;
    }

    public Worker() {
        this.active = false;
    }
}
