package com.ziola.shelter.workers;

import com.ziola.shelter.animals.AnimalDTO;
import com.ziola.shelter.animals.AnimalService;
import com.ziola.shelter.token.VerificationTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class WorkerController {

    private final WorkerService workerService;

    private final AnimalService animalService;

    private final VerificationTokenService verificationTokenService;


    @GetMapping("/addNewWorker")
    public String addNewWorker(@ModelAttribute("newWorker") Worker newWorker) {
        return "addingWorker";
    }

    @PostMapping("/addNewWorker")
    public String processAddingNewWorker(@Valid @ModelAttribute("newWorker") Worker workerToBeAdded, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "addingWorker";
        }
        if(workerService.checkIfExistsThenSave(workerToBeAdded)){
        redirectAttributes.addFlashAttribute("flash.workerAdded", "Dodano pracownika!");
        }else{
            redirectAttributes.addFlashAttribute("flash.workerAdded", "Adres email został już użyty");
        }
        return "redirect:/addNewWorker";
    }

    @GetMapping("/editDetails/worker/deleteWorker/{id}")
    public String deleteGivenWorker(@PathVariable("id") int id, RedirectAttributes attributes) {
        verificationTokenService.deleteTokenByWorkerId(id);
        workerService.deleteById(id);
        attributes.addFlashAttribute("flash.worker.changed", "Pracownik został usunięty z bazy");
        return "redirect:/admin/home";
    }

    @GetMapping("/showAllWorkers")
    public String showAllWorkers(Model model) {
        model.addAttribute("allWorkers", workerService.findAll());
        return "admin/home";
    }

    @GetMapping("seeDetails/worker/{id}")
    public String goToWorkerDetailsSite(@PathVariable("id") int workerId, Model model){
        WorkerDTOEditing workerDTO = workerService.findByIdAndConvertToDTO(workerId);
        model.addAttribute("worker", workerDTO);
        return "workersDetails";
    }

    @GetMapping("seeDetails/worker/addedAnimals/{id}")
    public String showAllAddedAnimalsByWorker(@PathVariable("id") int workerId, Model model) {
        List<AnimalDTO> allAnimalsDto = animalService.findByWorkerIdAndReturnList(workerId);
        model.addAttribute("allAnimals", allAnimalsDto);
        return "showAllAnimals";
    }

    @GetMapping("/editDetails/worker/{id}")
    public String goToEditWorkerSite(@PathVariable("id") int workerId, Model model) {
        WorkerDTOEditing workerDTOEditing = workerService.findByIdAndConvertToDTO(workerId);
        model.addAttribute("worker", workerDTOEditing);
        return "editWorker";
    }

    @PostMapping("/editDetails/worker/processEditWorker")
    public String processEditWorker(@Valid @ModelAttribute("worker") WorkerDTOEditing editedWorker, BindingResult result, RedirectAttributes redirectAttributes){
        if (result.hasErrors()) {
            return "editWorker";
        }
        workerService.findByIdAndCreateAndSaveWorker(editedWorker);
        redirectAttributes.addFlashAttribute("flash.worker.changed", "Dane pracownika zostały zaktualizowane");
        return "redirect:/admin/home";
    }
}
