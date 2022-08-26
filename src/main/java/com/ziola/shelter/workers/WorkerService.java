package com.ziola.shelter.workers;

import com.ziola.shelter.exceptions.EmailExistsException;
import com.ziola.shelter.security.VerificationToken;

public interface WorkerService {

    void saveWorker(Worker worker, String role);

    Worker registerNewUserAccount(WorkerDTO workerDTO) throws EmailExistsException;

    void createVerificationToken(Worker worker, String token);

    VerificationToken getVerificationToken(String token);

    void saverRegisteredWorker(Worker worker);
}
