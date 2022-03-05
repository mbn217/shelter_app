package com.ziola.shelter.workers.service;

import com.ziola.shelter.workers.domain.Worker;

public interface AddingWorkerService  {

  boolean checkIfExistsThenSave(Worker worker);
}
