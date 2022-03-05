package com.ziola.shelter.animals.service;

import com.ziola.shelter.animals.domain.Animal;
import com.ziola.shelter.aws.domain.Image;

public interface SaveAnimalService {

    void saveAnimal(Animal animalToSave, String emailOfWorker, Image image);
}
