package com.ziola.shelter.workers.controllers;

import com.ziola.shelter.workers.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShowingAllWorkersController {

    private WorkerRepository workerRepository;

    @Autowired
    public ShowingAllWorkersController(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    @GetMapping("/showAllWorkers")
    public String showAllWorkers(Model model) {
        model.addAttribute("allWorkers", workerRepository.findAll());
        return "allWorkersSite";
    }
}
