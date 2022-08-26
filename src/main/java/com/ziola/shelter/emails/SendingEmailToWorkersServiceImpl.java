package com.ziola.shelter.emails;

import com.ziola.shelter.workers.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SendingEmailToWorkersServiceImpl implements SendingEmailToWorkersService {

  private final static String message = "Uwaga! Zostało kilka wolnych miejsc, więc proszę uważnie rezerwować! Wolnych miejsc zostało: ";

  private final WorkerRepository workerRepository;
  private final EmailService emailService;

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
