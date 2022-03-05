package com.ziola.shelter.animals.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class AnimalDTO {

  @Size(min = 2, max = 20, message = "{animal.domain.size.error}")
  private String name;

  @NotNull(message = "{animal.domain.null.error}")
  private int age;

  @NotNull(message = "{animal.domain.null.specie}")
  private String specie;

  @NotNull(message = "{animal.domain.null.error}")
  @Size(min = 2, max = 150, message = "{animal.domain.null.size.description}")
  private String description;

  @NotNull(message = "{animal.domain.null.error}")
  @Size(min = 2, max = 150, message = "{animal.domain.null.size.health}")
  private String health;

  @NotNull(message = "{animal.domain.null.error}")
  @Size(min = 2, max = 150, message = "{animal.domain.null.size.health}")
  private String race;

  @NotNull(message = "{animal.domain.null.error}")
  private String sex;

  @NotNull(message = "{animal.domain.null.error}")
  private String city;

  private String linkToImage;

  private Integer idOfAnimalThatImageBelongsTo;

  public Integer getIdOfAnimalThatImageBelongsTo() {
    return idOfAnimalThatImageBelongsTo;
  }

  public void setIdOfAnimalThatImageBelongsTo(Integer idOfAnimalThatImageBelongsTo) {
    this.idOfAnimalThatImageBelongsTo = idOfAnimalThatImageBelongsTo;
  }

  public String getLinkToImage() {
    return linkToImage;
  }

  public void setLinkToImage(String linkToImage) {
    this.linkToImage = linkToImage;
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

  public AnimalDTO() {
  }

  public AnimalDTO(@Size(min = 2, max = 20, message = "{animal.domain.size.error}") String name,
      @NotNull(message = "{animal.domain.null.error}") int age,
      @NotNull(message = "{animal.domain.null.error}") String specie,
      @NotNull(message = "{animal.domain.null.error}") String description,
      @NotNull(message = "{animal.domain.null.error}") String health,
      @NotNull(message = "{animal.domain.null.error}") String race,
      @NotNull(message = "{animal.domain.null.error}") String sex,
      @NotNull(message = "{animal.domain.null.error}") String city) {
    this.name = name;
    this.age = age;
    this.specie = specie;
    this.description = description;
    this.health = health;
    this.race = race;
    this.sex = sex;
    this.city = city;
  }
}