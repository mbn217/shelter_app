package com.ziola.shelter.workers.controllers;

import com.ziola.shelter.workers.AddingWorkerService;
import com.ziola.shelter.workers.Worker;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
//@RequiredArgsConstructor
public class AddingNewWorkerController {

    private final AddingWorkerService addingWorkerService;

    public AddingNewWorkerController(AddingWorkerService addingWorkerService) {
        this.addingWorkerService = addingWorkerService;
    }

    @GetMapping("/addNewWorker")
    public String addNewWorker(@ModelAttribute("newWorker") Worker newWorker) {
        return "addingWorker";
    }

    @PostMapping("/addNewWorker")
    public String processAddingNewWorker(@Valid @ModelAttribute("newWorker") Worker workerToBeAdded, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "addingWorker";
        }
        if(addingWorkerService.checkIfExistsThenSave(workerToBeAdded)){
        redirectAttributes.addFlashAttribute("flash.workerAdded", "Dodano pracownika!");
        }else{
            redirectAttributes.addFlashAttribute("flash.workerAdded", "Adres email został już użyty");
        }
        return "redirect:/addNewWorker";
    }
}
