package com.ziola.shelter.animals.service.impl;

import com.ziola.shelter.animals.dto.AnimalDTO;
import com.ziola.shelter.animals.logic.RandomAnimals;
import com.ziola.shelter.animals.repository.AnimalRepository;
import com.ziola.shelter.animals.service.RandomAnimalsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RandomAnimalsServiceImpl implements RandomAnimalsService {

	private AnimalRepository animalRepository;
	private RandomAnimals randomAnimals;

	@Autowired
	public RandomAnimalsServiceImpl(AnimalRepository animalRepository, RandomAnimals randomAnimals) {
		this.animalRepository = animalRepository;
		this.randomAnimals = randomAnimals;
	}

	@Override
	public List<AnimalDTO> randomAnimalsList(int numberOfAnimals) {

		List<AnimalDTO> random = randomAnimals.randomAnimals(animalRepository.findAll(), numberOfAnimals);
		return random;
	}
}
