package com.ziola.shelter.animals.service.impl;

import com.ziola.shelter.animals.domain.Animal;
import com.ziola.shelter.animals.repository.AnimalRepository;
import com.ziola.shelter.animals.service.SaveAnimalService;
import com.ziola.shelter.aws.domain.Image;
import com.ziola.shelter.workers.domain.Worker;
import com.ziola.shelter.workers.repository.WorkerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SaveAnimalServiceImpl implements SaveAnimalService {

  private AnimalRepository animalRepository;
  private WorkerRepository workerRepository;

  @Autowired
  public SaveAnimalServiceImpl(AnimalRepository animalRepository, WorkerRepository workerRepository) {
    this.animalRepository = animalRepository;
    this.workerRepository = workerRepository;
  }

  public void saveAnimal(Animal animalToSave, String emailOfWorker, Image image) {
    Worker workerWhoIsAdding = workerRepository.findByEmail(emailOfWorker);
    animalToSave.setWorker(workerWhoIsAdding);
    animalToSave.setImage(image);
    animalRepository.save(animalToSave);
  }
}
