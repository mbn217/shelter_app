package com.ziola.shelter.animals.domain;


import com.ziola.shelter.aws.domain.Image;
import com.ziola.shelter.workers.domain.Worker;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@ToString
@RequiredArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "animal")
public class Animal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "animal_id")
    private Integer id;

    @Size(min = 2, max = 20, message = "Maksymalna liczba znaków 20!")
    @Column(name = "animal_name")
    private String name;

    @NotNull(message = "Nie może być puste!")
    @Column(name = "animal_age")
    private int age;

    @NotNull(message = "Nie może być puste!")
    @Column(name = "animal_specie")
    private String specie;

    @Size(max = 450)
    @NotNull(message = "{animal.domain.null.error}")
    @Column(name = "animal_description")
    private String description;

    @Size(max = 150)
    @NotNull(message = "Nie może być puste!")
    @Column(name = "animal_health")
    private String health;

    @Size(max = 45)
    @NotNull(message = "Nie może być puste!")
    @Column(name = "animal_race")
    private String race;

    @Size(max = 45)
    @NotNull(message ="Nie może być puste!")
    @Column(name = "animal_sex")
    private String sex;

    @Size(max = 20)
    @NotNull(message = "Nie może być puste!")
    @Column(name = "animal_city")
    private String city;

    @ManyToOne
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @OneToOne(targetEntity = Image.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id", nullable = false)
    private Image image;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Animal animal = (Animal) o;
        return id != null && Objects.equals(id, animal.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}