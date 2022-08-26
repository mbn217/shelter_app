package com.ziola.shelter.security;

import com.ziola.shelter.emails.EmailService;
import com.ziola.shelter.workers.Worker;
import com.ziola.shelter.workers.WorkerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {

    private final MessageSource messages;
    private final WorkerService workerService;
    private final EmailService emailService;
    private final TaskExecutor taskExecutor;

    @Autowired
    public RegistrationListener(@Qualifier("customMessages") MessageSource messages, WorkerService workerService, EmailService emailService, TaskExecutor taskExecutor) {
        this.messages = messages;
        this.workerService = workerService;
        this.emailService = emailService;
        this.taskExecutor = taskExecutor;
    }

    @Override
    public void onApplicationEvent(OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(OnRegistrationCompleteEvent event) {
        Worker worker = event.getWorker();
        String token = UUID.randomUUID().toString();
        workerService.createVerificationToken(worker, token);

        String recipientAddress = worker.getEmail();
        String subject = "Potwierdzenie rejestracji";
        String confirmationurl = event.getAppUrl() + "/registrationConfirm?token=" + token;
        String message = messages.getMessage("message.regSucc", null, event.getLocale())
                + " rn " + "https://shelter-demo.herokuapp.com" + confirmationurl;

        taskExecutor.execute(() -> emailService.sendSimpleMessage(recipientAddress, subject, message));
    }
}
