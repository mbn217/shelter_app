package com.ziola.shelter.util;

import com.ziola.shelter.animals.domain.Animal;
import com.ziola.shelter.animals.dto.AnimalDTO;
import com.ziola.shelter.animals.dto.AnimalDTORest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConverterDtoAnimalEntity {

    private final ModelMapper modelMapper;

    @Autowired
    public ConverterDtoAnimalEntity(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Animal convertToEntity(AnimalDTO animalDTO) {
        Animal newAnimal = modelMapper.map(animalDTO, Animal.class);
        if (animalDTO.getIdOfAnimalThatImageBelongsTo() != null)
            newAnimal.setId(animalDTO.getIdOfAnimalThatImageBelongsTo());
        return newAnimal;
    }

    public AnimalDTO convertToDto(Animal animal) {
        AnimalDTO animalDtO = modelMapper.map(animal, AnimalDTO.class);
        animalDtO.setLinkToImage(animal.getImage().getLinkToImage());
        return animalDtO;
    }

    public AnimalDTO convertToDtoWithId(Animal animal) {
        AnimalDTO animalDto = modelMapper.map(animal, AnimalDTO.class);
        if (animal.getImage() != null) animalDto.setLinkToImage(animal.getImage().getLinkToImage());
        animalDto.setIdOfAnimalThatImageBelongsTo(animal.getId());
        return animalDto;
    }

    public AnimalDTORest convertToDtoRest(Animal animal) {
        AnimalDTORest dtoRest = modelMapper.map(animal, AnimalDTORest.class);
        dtoRest.setLinkToImage(animal.getImage().getLinkToImage());
        return dtoRest;
    }
}
