package com.ziola.shelter.workers.service;

import com.ziola.shelter.exceptions.EmailExistsException;
import com.ziola.shelter.security.token.domain.VerificationToken;
import com.ziola.shelter.workers.domain.Worker;
import com.ziola.shelter.workers.dto.WorkerDTO;

public interface WorkerService {

    void saveWorker(Worker worker, String role);

    Worker findWorkerByEmail(String email);

    Worker registerNewUserAccount(WorkerDTO workerDTO) throws EmailExistsException;

    void createVerificationToken(Worker worker, String token);

    VerificationToken getVerificationToken(String token);

    void saverRegisteredWorker(Worker worker);

    Worker getWorker(String verificationToken);
}
