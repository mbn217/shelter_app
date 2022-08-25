package com.ziola.shelter.animals.domain;


import com.ziola.shelter.aws.domain.Image;
import com.ziola.shelter.workers.domain.Worker;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import javax.persistence.*;

@Data
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

    @Size(min = 2, max = 20, message = "{animal.domain.size.error}")
    @Column(name = "animal_name")
    private String name;


    @Column(name = "animal_age")
    @NotNull(message = "{animal.domain.null.error}" )
    private int age;

    @NotNull(message = "{animal.domain.null.error}" )
    @Column(name = "animal_specie")
    private String specie;

    @Size(max = 450)
    @NotNull(message = "{animal.domain.null.error}" )
    @Column(name = "animal_description")
    private String description;

    @Size(max = 150)
    @Column(name = "animal_health")
    @NotNull(message = "{animal.domain.null.error}" )
    private String health;

    @Size(max = 45)
    @Column(name = "animal_race")
    @NotNull(message = "{animal.domain.null.error}" )
    private String race;

    @Size(max = 45)
    @Column(name = "animal_sex")
    @NotNull(message = "{animal.domain.null.error}" )
    private String sex;

    @Size(max = 20)
    @Column(name = "animal_city")
    @NotNull(message = "{animal.domain.null.error}" )
    private String city;

    @ManyToOne
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @OneToOne(targetEntity = Image.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "image_id", nullable = false)
    private Image image;
}