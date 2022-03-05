package com.ziola.shelter.workers.controllers;

import com.ziola.shelter.workers.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/editDetails/worker/deleteWorker")
public class DeleteWorkerController {

    private WorkerRepository workerRepository;

    @Autowired
    public DeleteWorkerController(WorkerRepository workerRepository) {
        this.workerRepository = workerRepository;
    }

    @GetMapping("/{id}")
    public String deleteGivenWorker(@PathVariable("id") int id, RedirectAttributes attributes) {
        workerRepository.deleteById(id);
        attributes.addFlashAttribute("flash.worker.changed", "Pracownik został usunięty z bazy");
        return "redirect:/admin/home";
    }
}
