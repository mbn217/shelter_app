package com.ziola.shelter.animals.service;

import com.ziola.shelter.animals.domain.Animal;
import com.ziola.shelter.animals.dto.AnimalDTO;
import com.ziola.shelter.aws.domain.Image;
import org.springframework.web.multipart.MultipartFile;

public interface AnimalService {

    void saveAnimal(Animal animalToSave, Image image);

    int freePlacesLeft();

    void takeDTOConvertAndSave(AnimalDTO newAnimalDto, MultipartFile image);
}
