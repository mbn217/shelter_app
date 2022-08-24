package com.ziola.shelter.animals.controllers;

import com.ziola.shelter.animals.dto.AnimalDTO;
import com.ziola.shelter.animals.logic.AllCitiesFinderService;
import com.ziola.shelter.animals.repository.AnimalRepository;
import com.ziola.shelter.animals.service.AnimalService;
import com.ziola.shelter.util.ConverterDtoAnimalEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/showAllAnimals/{specie}")
public class ShowAllAnimalsController {

  private final int maximumAnimalsInShelter = 10;
  private final AnimalRepository animalRepository;
  private  final AllCitiesFinderService allCitiesFinderService;
  private final ConverterDtoAnimalEntity converterDtoAnimalEntity;
  private final AnimalService animalService;


  @GetMapping
  public String showAllAnimals(@PathVariable("specie") String specie, Model model) {
	List<AnimalDTO> allAnimalsDto = new ArrayList<>();
    if (specie.equals("all")) {
      animalRepository.findAll()
              .forEach(animal -> allAnimalsDto.add(converterDtoAnimalEntity.convertToDtoWithId(animal)));
      model.addAttribute("allAnimals", allAnimalsDto);
    } else {
      animalRepository.findBySpecie(specie)
    		  .forEach(animal -> allAnimalsDto.add(converterDtoAnimalEntity.convertToDtoWithId(animal)));
	model.addAttribute("allAnimals", allAnimalsDto);
    }
    model.addAttribute("cities", allCitiesFinderService.collectCitiesToList());
    checkIfShelterEmpty(model);
    return "showAllAnimals";
  }

  @GetMapping("/{cities}")
  public String showAnimalsByCity(@PathVariable("specie") String specie, @PathVariable("cities") String cityOfAnimal, Model model) {
	  List<AnimalDTO> allAnimalsDto = new ArrayList<>();
    model.addAttribute("cities", allCitiesFinderService.collectCitiesToList());
    animalRepository.findByCityAndSpecie(cityOfAnimal, specie)
    		 .forEach(animal -> allAnimalsDto.add(converterDtoAnimalEntity.convertToDtoWithId(animal)));
	model.addAttribute("allAnimals", allAnimalsDto);
    return "showAllAnimals";
  }

  private void checkIfShelterEmpty(Model model) {
    if (animalService.freePlacesLeft() == maximumAnimalsInShelter) {
      model.addAttribute("emptyShelterFlash", "Hotel jest pusty. Można dodać zwierzaka");
    }
  }
}
