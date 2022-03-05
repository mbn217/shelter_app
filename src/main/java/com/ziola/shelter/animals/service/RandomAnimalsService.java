package com.ziola.shelter.animals.service;

import com.ziola.shelter.animals.dto.AnimalDTO;

import java.util.List;

public interface RandomAnimalsService {

	List<AnimalDTO> randomAnimalsList(int numberOfElements);
}
