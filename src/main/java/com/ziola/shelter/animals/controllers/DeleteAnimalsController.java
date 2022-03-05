package com.ziola.shelter.animals.controllers;

import com.ziola.shelter.animals.domain.Animal;
import com.ziola.shelter.animals.repository.AnimalRepository;
import com.ziola.shelter.aws.repository.ImageRepository;
import com.ziola.shelter.aws.service.BucketsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/deleteAnimal")
public class DeleteAnimalsController {

	private AnimalRepository animalRepository;
	private BucketsService bucketsService;
	private final static String BUCKETNAME = "shelteriploadimages";
	private ImageRepository imageRepository;

	@Autowired
	public DeleteAnimalsController(AnimalRepository animalRepository, BucketsService bucketsService, ImageRepository imageRepository) {
		this.animalRepository = animalRepository;
		this.bucketsService = bucketsService;
		this.imageRepository = imageRepository;
	}


	@Secured("ROLE_ADMIN")
	@GetMapping("/{id}")
	public String deleteAnimalById(@PathVariable("id") int animalId, RedirectAttributes redirectAttributes) {
		Animal animal = animalRepository.findById(animalId);
		String[] temp = animal.getImage().getLinkToImage().split("https://s3.eu-central-1.amazonaws.com/shelteriploadimages/");
		String objectName = temp[1];
		if(bucketsService.listHasObject(BUCKETNAME, objectName)){
			bucketsService.deleteAnObject(BUCKETNAME, animal);
		}
		imageRepository.delete(animal.getImage());
		animalRepository.deleteById(animalId);
		redirectAttributes.addFlashAttribute("animalDeleted", "Zwierze zostało usunięte z bazy");
		return "redirect:/showAllAnimals/all";
	}
}
