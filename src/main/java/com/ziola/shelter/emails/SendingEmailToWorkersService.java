package com.ziola.shelter.emails;

public interface SendingEmailToWorkersService {

    void sendEmailToAllWorkers(int tempInt);

    void checkIfEmailCanBeSent(int freePlaces, int minimumPlacesToSendEmails);
}
