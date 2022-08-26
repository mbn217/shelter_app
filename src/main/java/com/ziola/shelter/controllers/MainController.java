package com.ziola.shelter.controllers;

import com.ziola.shelter.animals.RandomAnimalsService;
import com.ziola.shelter.aws.BucketsService;
import com.ziola.shelter.emails.domain.MessageEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class MainController {

	private final static int NUMBER_OF_RANDOM_ANIMALS = 3;
	private final RandomAnimalsService randomAnimalsService;
	private final BucketsService bucketsService;

	@Autowired
	public MainController(RandomAnimalsService randomAnimalsService, BucketsService bucketsService) {
		this.randomAnimalsService = randomAnimalsService;
		this.bucketsService = bucketsService;
	}

	@GetMapping
	public String allAnimals(Model model) {
		MessageEntity messageEntity = new MessageEntity();
		if(!bucketsService.checkIfExists("shelteriploadimages")){
			bucketsService.createBucket("shelteriploadimages");
		}
		model.addAttribute("randomAnimals", randomAnimalsService.randomAnimalsList(NUMBER_OF_RANDOM_ANIMALS));
		model.addAttribute("newMessage", messageEntity);
		return "index";
	}
}
