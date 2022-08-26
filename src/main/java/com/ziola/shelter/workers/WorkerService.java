package com.ziola.shelter.workers;

import com.ziola.shelter.exceptions.EmailExistsException;

public interface WorkerService {

    void saveWorker(Worker worker, String role);

    Worker registerNewUserAccount(WorkerDTO workerDTO) throws EmailExistsException;

    void saverRegisteredWorker(Worker worker);

    Worker createWorkerAccount(WorkerDTO workerDTO);

    boolean checkIfExistsThenSave(Worker worker);

    void deleteById(int id);

    Object findAll();

    WorkerDTOEditing findByIdAndConvertToDTO(int workerId);
}
