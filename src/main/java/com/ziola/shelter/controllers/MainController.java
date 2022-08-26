package com.ziola.shelter.controllers;

import com.ziola.shelter.animals.RandomAnimalsService;
import com.ziola.shelter.emails.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/")
public class MainController {

	private final static int NUMBER_OF_RANDOM_ANIMALS = 3;
	private final RandomAnimalsService randomAnimalsService;

	@GetMapping
	public String allAnimals(Model model) {
		Message messageEntity = new Message();
		model.addAttribute("randomAnimals", randomAnimalsService.randomAnimalsList(NUMBER_OF_RANDOM_ANIMALS));
		model.addAttribute("newMessage", messageEntity);
		return "index";
	}
}
