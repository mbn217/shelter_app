package com.ziola.shelter.animals.logic;

import com.ziola.shelter.animals.domain.Animal;
import com.ziola.shelter.animals.dto.AnimalDTO;
import com.ziola.shelter.util.ConverterDtoAnimalEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RandomAnimals {

  private ConverterDtoAnimalEntity converter;

  @Autowired
  public RandomAnimals(ConverterDtoAnimalEntity converter) {
    this.converter = converter;
  }

  public List<AnimalDTO> randomAnimals(List<Animal> listOfAnimals, int numberOfRandomElements) {
    List<AnimalDTO> randomAnimals = new ArrayList<>();
    int numberOfRandomElementsChecked = checkIfCorrectNumber(numberOfRandomElements, listOfAnimals);
    for (int i = 1; i <= numberOfRandomElementsChecked; i++) {
        AnimalDTO convertToDtoWithId = converter.convertToDtoWithId(listOfAnimals.get(pickRandomNumber(listOfAnimals)));
        boolean animalExists = randomAnimals.stream()
        .anyMatch(t -> t.getIdOfAnimalThatImageBelongsTo().equals(convertToDtoWithId.getIdOfAnimalThatImageBelongsTo()));
        if(animalExists) i--;
        else randomAnimals.add(convertToDtoWithId);
    }
    return randomAnimals;
  }

  private int pickRandomNumber(List<Animal> listOfAnimals) {
    int i = (int) (Math.random() * listOfAnimals.size());
    return i;
  }

  private int checkIfCorrectNumber(int numberToCheck, List<Animal> listOfAnimals) {

    if (numberToCheck < 0) {
      return 0;
    }
    else if(listOfAnimals.size() < numberToCheck){
      if(listOfAnimals.size() == 1){
        return 1;
      }else{
      return listOfAnimals.size();
      }
    }
    else {
      return numberToCheck;
    }
  }
}