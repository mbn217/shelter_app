package com.ziola.shelter.animals.controllers;

import com.ziola.shelter.animals.domain.Animal;
import com.ziola.shelter.animals.dto.AnimalDTO;
import com.ziola.shelter.animals.repository.AnimalRepository;
import com.ziola.shelter.util.ConverterDtoAnimalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/seeDetails/animal")
public class AnimalsDetailsController {

	private AnimalRepository animalRepository;
	private ConverterDtoAnimalEntity converterDtoAnimalEntity;
	
	@Autowired
	public AnimalsDetailsController(AnimalRepository animalRepository, ConverterDtoAnimalEntity converterDtoAnimalEntity) {
		this.animalRepository = animalRepository;
		this.converterDtoAnimalEntity = converterDtoAnimalEntity;
	}

	@GetMapping("/{id}")
	public String seeDetailsAnimal(@PathVariable("id") int animalId, Model model) {
		Animal animalById = animalRepository.findById(animalId);
		AnimalDTO animalDto = converterDtoAnimalEntity.convertToDto(animalById);
		model.addAttribute("animal", animalDto);
		return "animalsDetails";
	}
}
