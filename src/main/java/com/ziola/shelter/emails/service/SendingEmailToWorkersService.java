package com.ziola.shelter.emails.service;

public interface SendingEmailToWorkersService {

    void sendEmailToAllWorkers(int tempInt);

    void checkIfEmailCanBeSent(int freePlaces, int minimumPlacesToSendEmails);
}
