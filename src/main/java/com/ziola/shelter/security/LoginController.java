package com.ziola.shelter.security;

import com.ziola.shelter.workers.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class LoginController {

	private WorkerRepository workerRepository;

	@Autowired
	public LoginController(WorkerRepository workerRepository) {
		this.workerRepository = workerRepository;
	}

	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/admin/home")
	public String home(Model model) {
		model.addAttribute("allUsers", workerRepository.findAll());
		return "admin/home";
	}
}
