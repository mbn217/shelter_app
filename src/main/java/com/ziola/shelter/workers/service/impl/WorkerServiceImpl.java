package com.ziola.shelter.workers.service.impl;

import com.ziola.shelter.exceptions.EmailExistsException;
import com.ziola.shelter.security.token.domain.VerificationToken;
import com.ziola.shelter.security.token.repository.TokenRepository;
import com.ziola.shelter.workers.domain.Role;
import com.ziola.shelter.workers.domain.Worker;
import com.ziola.shelter.workers.dto.WorkerDTO;
import com.ziola.shelter.workers.repository.RoleRepository;
import com.ziola.shelter.workers.repository.WorkerRepository;
import com.ziola.shelter.workers.service.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class WorkerServiceImpl implements WorkerService {

  private WorkerRepository workerRepository;
  private RoleRepository roleRepository;
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  private TokenRepository tokenRepository;

  @Autowired
  public WorkerServiceImpl(WorkerRepository workerRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder, TokenRepository tokenRepository) {
    this.workerRepository = workerRepository;
    this.roleRepository = roleRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    this.tokenRepository = tokenRepository;
  }

  private String welcomeMessage = "Witamy w PodPsem.pl. Miło nam, że dołączyłeś do nas!";

  public Worker findWorkerByEmail(String email) {
    return workerRepository.findByEmail(email);
  }

  @Override
  public Worker registerNewUserAccount(WorkerDTO workerDTO)  throws EmailExistsException {
    if (emailExists(workerDTO.getEmail())) {
      throw new EmailExistsException("Już istnieje konto przypisanego do tego adresu email: " + workerDTO.getEmail());
    }
    Worker worker = new Worker();
    worker.setName(workerDTO.getName());
    worker.setLastName(workerDTO.getLastName());
    worker.setEmail(workerDTO.getEmail());
    worker.setPassword(setEncodePasswordNewWorker(workerDTO));
    worker.setRole(roleRepository.findByRole("ADMIN"));
    return workerRepository.save(worker);
  }

  @Override
  public void createVerificationToken(Worker worker, String token) {
    VerificationToken myToken = new VerificationToken(token, worker);
    tokenRepository.save(myToken);
  }

  @Override
  public VerificationToken getVerificationToken(String token) {
    return tokenRepository.findByToken(token);
  }

  @Override
  public void saverRegisteredWorker(Worker worker) {
    worker.setActive(true);
    workerRepository.save(worker);
  }

  @Override
  public Worker getWorker(String verificationToken) {
    return tokenRepository.findByToken(verificationToken).getWorker();
  }

  private boolean emailExists(String email) {
    Worker worker = workerRepository.findByEmail(email);
    return worker != null;
  }

  public void saveWorker(Worker worker, String role) {
    Role workerRole = roleRepository.findByRole(role);
    worker.setRole(workerRole);
    workerRepository.save(worker);
  }

  private String setEncodePasswordNewWorker(WorkerDTO workerDTO) {
    return bCryptPasswordEncoder.encode(workerDTO.getPassword());
  }
}
