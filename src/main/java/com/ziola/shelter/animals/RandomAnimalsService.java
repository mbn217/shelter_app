package com.ziola.shelter.animals;

import com.ziola.shelter.animals.AnimalDTO;

import java.util.List;

public interface RandomAnimalsService {

	List<AnimalDTO> randomAnimalsList(int numberOfElements);
}
