package com.ziola.shelter.workers.service.impl;

import com.ziola.shelter.workers.domain.Worker;
import com.ziola.shelter.workers.repository.WorkerRepository;
import com.ziola.shelter.workers.service.AddingWorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddingWorkerServiceImpl implements AddingWorkerService {

  private WorkerRepository workerRepository;

  @Autowired
  public AddingWorkerServiceImpl(WorkerRepository workerRepository) {
    this.workerRepository = workerRepository;
  }

  @Override
  public boolean checkIfExistsThenSave(Worker worker) {
    if (emailExists(worker.getEmail())) {
    return false;
    }else{
      workerRepository.save(worker);
      return true;
    }
  }

  private boolean emailExists(String emailToFind) {
    Worker worker = workerRepository.findByEmail(emailToFind);
    return worker != null;
  }
}
