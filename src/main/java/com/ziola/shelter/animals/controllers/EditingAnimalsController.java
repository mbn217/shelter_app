package com.ziola.shelter.animals.controllers;

import com.ziola.shelter.animals.domain.Animal;
import com.ziola.shelter.animals.dto.AnimalDTO;
import com.ziola.shelter.animals.repository.AnimalRepository;
import com.ziola.shelter.util.ConverterDtoAnimalEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/editDetails/animal")
public class EditingAnimalsController {

    private AnimalRepository animalRepository;
    private ConverterDtoAnimalEntity converterDtoAnimalEntity;

    @Autowired
    public EditingAnimalsController(AnimalRepository animalRepository, ConverterDtoAnimalEntity converterDtoAnimalEntity) {
        this.animalRepository = animalRepository;
        this.converterDtoAnimalEntity = converterDtoAnimalEntity;
    }

    @GetMapping("/{id}")
    public String editDog(@PathVariable("id") int animalId, Model model) {
        Animal foundAnimal = animalRepository.findById(animalId);
        AnimalDTO newAnimalDto = converterDtoAnimalEntity.convertToDtoWithId(foundAnimal);
        model.addAttribute("newAnimalDto", newAnimalDto);
        return "addingAnimal";
    }
}
