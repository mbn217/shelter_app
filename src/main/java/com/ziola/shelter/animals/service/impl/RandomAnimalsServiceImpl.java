package com.ziola.shelter.animals.service.impl;

import com.ziola.shelter.animals.domain.Animal;
import com.ziola.shelter.animals.dto.AnimalDTO;
import com.ziola.shelter.animals.repository.AnimalRepository;
import com.ziola.shelter.animals.service.RandomAnimalsService;
import com.ziola.shelter.util.ConverterDtoAnimalEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RandomAnimalsServiceImpl implements RandomAnimalsService {

    private final AnimalRepository animalRepository;
    private final ConverterDtoAnimalEntity converterDtoAnimalEntity;

    @Override
    public List<AnimalDTO> randomAnimalsList(int numberOfAnimals) {

        return randomAnimals(animalRepository.findAll(), numberOfAnimals);
    }

    public List<AnimalDTO> randomAnimals(List<Animal> listOfAnimals, int numberOfRandomElements) {
        List<AnimalDTO> randomAnimals = new ArrayList<>();
        int numberOfRandomElementsChecked = checkIfCorrectNumber(numberOfRandomElements, listOfAnimals);
        for (int i = 1; i <= numberOfRandomElementsChecked; i++) {
            AnimalDTO convertToDtoWithId = converterDtoAnimalEntity.convertToDtoWithId(listOfAnimals.get(pickRandomNumber(listOfAnimals)));
            boolean animalExists = randomAnimals.stream()
                    .anyMatch(randomAnimal -> randomAnimal.getIdOfAnimalThatImageBelongsTo().equals(convertToDtoWithId.getIdOfAnimalThatImageBelongsTo()));
            if (animalExists) i--;
            else randomAnimals.add(convertToDtoWithId);
        }
        return randomAnimals;
    }

    private int pickRandomNumber(List<Animal> listOfAnimals) {
        return (int) (Math.random() * listOfAnimals.size());
    }

    private int checkIfCorrectNumber(int numberToCheck, List<Animal> listOfAnimals) {
        if (numberToCheck < 0) {
            return 0;
        } else return Math.min(listOfAnimals.size(), numberToCheck);
    }
}
