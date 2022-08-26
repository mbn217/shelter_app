package com.ziola.shelter.workers;

import com.ziola.shelter.exceptions.EmailExistsException;
import com.ziola.shelter.token.VerificationToken;

public interface WorkerService {

    void saveWorker(Worker worker, String role);

    Worker registerNewUserAccount(WorkerDTO workerDTO) throws EmailExistsException;

    VerificationToken getVerificationToken(String token);

    void saverRegisteredWorker(Worker worker);

    Worker createWorkerAccount(WorkerDTO workerDTO);
}
