package com.ziola.shelter.workers.controllers;

import com.ziola.shelter.animals.AnimalDTO;
import com.ziola.shelter.util.ConvertWorkerDTOEditingEntity;
import com.ziola.shelter.util.ConverterDtoAnimalEntity;
import com.ziola.shelter.workers.domain.Worker;
import com.ziola.shelter.workers.dto.WorkerDTOEditing;
import com.ziola.shelter.workers.repository.WorkerRepository;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
