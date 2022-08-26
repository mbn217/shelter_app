package com.ziola.shelter.emails;

import com.ziola.shelter.workers.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendingEmailToWorkersServiceImpl implements SendingEmailToWorkersService {

  private final String message = "Uwaga! Zostało kilka wolnych miejsc, więc proszę uważnie rezerwować! Wolnych miejsc zostało: ";

  private WorkerRepository workerRepository;
  private EmailService emailService;

  @Autowired
  public SendingEmailToWorkersServiceImpl(WorkerRepository workerRepository, EmailService emailService) {
    this.workerRepository = workerRepository;
    this.emailService = emailService;
  }

  @Override
  public void sendEmailToAllWorkers(int tempInt) {
    workerRepository.findAll()
        .forEach(worker -> emailService.sendSimpleMessage(worker.getEmail(), worker.getName(), message + tempInt));
  }

  @Override
  public void checkIfEmailCanBeSent(int freePlaces, int minimumPlacesToSendEmails) {
    if (freePlaces < minimumPlacesToSendEmails) {
      sendEmailToAllWorkers(freePlaces);
    }
  }
}
