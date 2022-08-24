package com.ziola.shelter.workers.controllers;

import com.ziola.shelter.util.ConvertWorkerDTOEditingEntity;
import com.ziola.shelter.workers.domain.Worker;
import com.ziola.shelter.workers.dto.WorkerDTOEditing;
import com.ziola.shelter.workers.repository.WorkerRepository;
import com.ziola.shelter.workers.service.WorkerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequiredArgsConstructor
@RequestMapping("/editDetails/worker")
public class EditingWorkerController {

  private WorkerRepository workerRepository;
  private ConvertWorkerDTOEditingEntity convertWorkerDTOEditingEntity;
  private WorkerService workerService;

  @Autowired
  public EditingWorkerController(WorkerRepository workerRepository, ConvertWorkerDTOEditingEntity convertWorkerDTOEditingEntity, WorkerService workerService) {
    this.workerRepository = workerRepository;
    this.convertWorkerDTOEditingEntity = convertWorkerDTOEditingEntity;
    this.workerService = workerService;
  }

  @GetMapping("/{id}")
  public String goToEditWorkerSite(@PathVariable("id") int workerId, Model model) {
    Worker workerFound = workerRepository.findById(workerId);
    WorkerDTOEditing workerDTOEditing = convertWorkerDTOEditingEntity.convertToDto(workerFound);
    model.addAttribute("worker", workerDTOEditing);
    return "editWorker";
  }

  @PostMapping("/processEditWorker")
  public String processEditWorker(@Valid @ModelAttribute("worker") WorkerDTOEditing editedWorker, BindingResult result, RedirectAttributes redirectAttributes){
    if (result.hasErrors()) {
      return "editWorker";
    }
    int id = editedWorker.getId();
    Worker worker = workerRepository.findById(id);
    worker.setName(editedWorker.getName());
    worker.setLastName(editedWorker.getLastName());
    worker.setEmail(editedWorker.getEmail());
    worker.setActive(editedWorker.isActive());
    checkWhichRoleAndSave(editedWorker, worker);
    redirectAttributes.addFlashAttribute("flash.worker.changed", "Dane pracownika zostały zaktualizowane");
    return "redirect:/admin/home";
  }

  private void checkWhichRoleAndSave(WorkerDTOEditing editedWorker, Worker worker) {
    if (editedWorker.getIsAdmin() == 1) {
      workerService.saveWorker(worker, "ADMIN");
    }else{
      workerService.saveWorker(worker, "USER");
    }
  }
}
