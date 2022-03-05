package com.ziola.shelter.animals.logic;

import com.ziola.shelter.animals.domain.Animal;
import com.ziola.shelter.animals.repository.AnimalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AllCitiesFinderService {

  private AnimalRepository animalRepository;

  @Autowired
  public AllCitiesFinderService(AnimalRepository animalRepository) {
    this.animalRepository = animalRepository;
  }

  public List<String> collectCitiesToList() {

    return animalRepository.findAll().stream()
        .map(Animal::getCity).distinct().collect(java.util.stream.Collectors.toList());
  }
}
