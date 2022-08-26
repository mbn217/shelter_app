package com.ziola.shelter.workers.controllers;

import com.ziola.shelter.animals.AnimalDTO;
import com.ziola.shelter.util.ConvertWorkerDTOEditingEntity;
import com.ziola.shelter.util.ConverterDtoAnimalEntity;
import com.ziola.shelter.workers.Worker;
import com.ziola.shelter.workers.WorkerDTOEditing;
import com.ziola.shelter.workers.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/seeDetails/worker")
public class WorkerDetailController {

  private WorkerRepository workerRepository;
  private ConvertWorkerDTOEditingEntity converterDtoWorkerEntity;
  private ConverterDtoAnimalEntity converterDtoAnimalEntity;

  @Autowired
  public WorkerDetailController(WorkerRepository workerRepository, ConvertWorkerDTOEditingEntity converterDtoWorkerEntity, ConverterDtoAnimalEntity converterDtoAnimalEntity) {
    this.workerRepository = workerRepository;
    this.converterDtoWorkerEntity = converterDtoWorkerEntity;
    this.converterDtoAnimalEntity = converterDtoAnimalEntity;
  }
    @GetMapping("/{id}")
  public String goToWorkerDetailsSite(@PathVariable("id") int workerId, Model model){
    Worker workerFound = workerRepository.findById(workerId);
    WorkerDTOEditing workerDTO = converterDtoWorkerEntity.convertToDto(workerFound);
    model.addAttribute("worker", workerDTO);
    return "workersDetails";
  }

  @GetMapping("/addedAnimals/{id}")
  public String showAllAddedAnimalsByWorker(@PathVariable("id") int workerId, Model model) {
    Worker workerFound = workerRepository.findById(workerId);
    List<AnimalDTO> allAnimalsDto = new ArrayList<>();
    workerFound.getAnimals()
    .forEach(animal -> allAnimalsDto.add(converterDtoAnimalEntity.convertToDtoWithId(animal)));
    model.addAttribute("allAnimals", allAnimalsDto);
    return "showAllAnimals";
  }
}
