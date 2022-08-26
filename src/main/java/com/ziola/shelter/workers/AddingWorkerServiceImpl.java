package com.ziola.shelter.workers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
@RequiredArgsConstructor
@Service
public class AddingWorkerServiceImpl implements AddingWorkerService {

  private final WorkerRepository workerRepository;

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
