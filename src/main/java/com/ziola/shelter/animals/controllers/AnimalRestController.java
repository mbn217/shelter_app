package com.ziola.shelter.animals.controllers;

import com.ziola.shelter.animals.domain.Animal;
import com.ziola.shelter.animals.dto.AnimalDTORest;
import com.ziola.shelter.animals.repository.AnimalRepository;
import com.ziola.shelter.util.ConverterDtoAnimalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest")
public class AnimalRestController {

    private final AnimalRepository animalRepository;
    private final ConverterDtoAnimalEntity converter;

    @Autowired
    public AnimalRestController(AnimalRepository animalRepository, ConverterDtoAnimalEntity converter) {
        this.animalRepository = animalRepository;
        this.converter = converter;
    }

    @GetMapping
    public List<AnimalDTORest> getAnimals() {
        List<Animal> animals = animalRepository.findAll();
        return animals.stream()
                .map(converter::convertToDtoRest)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public Animal findById(@PathVariable("id") Integer id) {
        return animalRepository.findById(id).orElseThrow();
    }
}
