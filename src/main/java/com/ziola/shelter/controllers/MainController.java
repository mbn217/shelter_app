package com.ziola.shelter.controllers;

import com.ziola.shelter.animals.service.RandomAnimalsService;
import com.ziola.shelter.aws.service.BucketsService;
import com.ziola.shelter.emails.domain.MessageEntity;
import com.ziola.shelter.workers.service.impl.CreateRoles;
import com.ziola.shelter.workers.service.impl.CreateUserWhenStarted;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping({"/", "/home"})
public class MainController {

	private RandomAnimalsService randomAnimalsService;
	private CreateRoles createRoles;
	private CreateUserWhenStarted createUserWhenStarted;
	private int numberOfRandomAnimals = 3;
	private BucketsService bucketsService;

	@Autowired
	public MainController(RandomAnimalsService randomAnimalsService, CreateRoles createRoles, CreateUserWhenStarted createUserWhenStarted, BucketsService bucketsService) {
		this.randomAnimalsService = randomAnimalsService;
		this.createRoles = createRoles;
		this.createUserWhenStarted = createUserWhenStarted;
		this.bucketsService = bucketsService;
	}

	@GetMapping
	public String allAnimals(Model model) {
		MessageEntity messageEntity = new MessageEntity();
		createRoles.createRolesAdminAndUser();
		createUserWhenStarted.createAdmin();
		if(!bucketsService.checkIfExists("shelteriploadimages")){
			bucketsService.createBucket("shelteriploadimages");
		}
		model.addAttribute("randomAnimals", randomAnimalsService.randomAnimalsList(numberOfRandomAnimals));
		model.addAttribute("newMessage", messageEntity);
		return "index";
	}
}
