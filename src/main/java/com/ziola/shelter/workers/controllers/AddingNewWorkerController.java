package com.ziola.shelter.workers.controllers;

import com.ziola.shelter.workers.domain.Worker;
import com.ziola.shelter.workers.service.AddingWorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class AddingNewWorkerController {

    private final AddingWorkerService addingWorkerService;

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
        return "redirect:/addNewWorker";
        }else{
            redirectAttributes.addFlashAttribute("flash.workerAdded", "Adres email został już użyty");
            return "redirect:/addNewWorker";
        }
    }
}
