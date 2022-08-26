package com.ziola.shelter.animals;

import com.ziola.shelter.image.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AnimalService {

    void saveAnimal(Animal animalToSave, Image image);

    int freePlacesLeft();

    void takeDTOConvertAndSave(AnimalDTO newAnimalDto, MultipartFile image);

    AnimalDTO findAnimalByIdAndConvertToDTO(int animalId);

    void findAndDeleteAnimal(int animalId);

    List<AnimalDTO> findAllAnimalsBySpecie(String specie);

    List<String> collectCitiesToList();

    List<AnimalDTO> findAllAnimalsBySpecieAndCity(String cityOfAnimal);

    List<AnimalDTO> findByWorkerIdAndReturnList(int workerId);
}
