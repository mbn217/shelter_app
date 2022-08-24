package com.ziola.shelter.animals.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AnimalDTO {

  @Size(min = 2, max = 20)
  private String name;

  @NotNull
  private int age;

  @NotNull
  private String specie;

  @NotNull
  @Size(min = 2, max = 150)
  private String description;

  @NotNull
  @Size(min = 2, max = 150)
  private String health;

  @NotNull
  @Size(min = 2, max = 150)
  private String race;

  @NotNull
  private String sex;

  @NotNull
  private String city;

  private String linkToImage;

  private Integer idOfAnimalThatImageBelongsTo;
}