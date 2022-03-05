package com.ziola.shelter.animals.logic;

import com.ziola.shelter.animals.repository.AnimalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HowManyPlaceLeft {

  private AnimalRepository animalRepository;
  private int maximumAnimals = 10;

  @Autowired
  public HowManyPlaceLeft(AnimalRepository animalRepository) {
    this.animalRepository = animalRepository;
  }

  public int freePlacesLeft() {
    return maximumAnimals - animalRepository.findAll().size();
  }
}

