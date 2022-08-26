package com.ziola.shelter.workers;

import com.ziola.shelter.animals.Animal;
import com.ziola.shelter.role.Role;
import com.ziola.shelter.token.VerificationToken;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

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
    @Column(name = "worker_id")
    private Integer id;
    @NotEmpty(message = "*Wprowadź imię")
    @Column(name = "worker_name")
    private String name;
    @NotEmpty(message = "*Wprowadź nazwisko")
    @Column(name = "worker_last_name")
    private String lastName;
    @Email(message = "*Wprowadź poprawny adres", regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$")
    @NotEmpty(message = "*Wprowadź poprawny adres")
    @Column(name = "worker_email")
    private String email;

    @Column(name = "worker_password")
//    @Length(min = 5, message = "*Hasło musi mieć przynajmniej 5 znaków")
    @NotEmpty(message = "*Wprowadź hasło")
    private String password;
    @Column(name = "worker_active")
    private boolean active;

    @OneToOne(mappedBy = "worker")
    private VerificationToken verificationToken;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "worker")
    private List<Animal> animals;

    public Worker() {
        this.active = false;
    }
}
