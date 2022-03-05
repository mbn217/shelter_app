package com.ziola.shelter.emails.service.impl;

import com.ziola.shelter.emails.service.EmailService;
import com.ziola.shelter.emails.service.SendingEmailToWorkersService;
import com.ziola.shelter.workers.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class SendingEmailToWorkersServiceImpl implements SendingEmailToWorkersService {

  private String message = "Uwaga! Zostało kilka wolnych miejsc, więc proszę uważnie rezerwować! Wolnych miejsc zostało: ";
  private String bossEmail = "tomek.ziola@gmail.com";

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
