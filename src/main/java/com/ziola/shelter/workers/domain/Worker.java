package com.ziola.shelter.workers.domain;

import com.ziola.shelter.animals.domain.Animal;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "worker")
public class Worker {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workers_id")
    private Integer id;
    @Column(name = "workers_name")
    @NotEmpty(message = "*Wprowadź imię")
    private String name;
    @Column(name = "workers_last_name")
    @NotEmpty(message = "*Wprowadź nazwisko")
    private String lastName;
    @Column(name = "workers_email")
    @Email(message = "*Wprowadź poprawny adres")
    @NotEmpty(message = "*Wprowadź poprawny adres")
    private String email;
    @Column(name = "workers_password")
    @Length(min = 5, message = "*Hasło musi mieć przynajmniej 5 znaków")
    @NotEmpty(message = "*Wprowadź hasło")
    private String password;
    @Column(name = "workers_active")
    private boolean active;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "worker")
    private List<Animal> animals;

    public Worker() {
        this.active = false;
    }
}
