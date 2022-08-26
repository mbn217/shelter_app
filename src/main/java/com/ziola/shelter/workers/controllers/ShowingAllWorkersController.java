package com.ziola.shelter.workers.controllers;

import com.ziola.shelter.workers.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
@RequiredArgsConstructor
@Controller
public class ShowingAllWorkersController {

    private final WorkerRepository workerRepository;

    @GetMapping("/showAllWorkers")
    public String showAllWorkers(Model model) {
        model.addAttribute("allWorkers", workerRepository.findAll());
        return "allWorkersSite";
    }
}
