package com.ziola.shelter.emails.controllers;

import com.ziola.shelter.emails.domain.Message;
import com.ziola.shelter.emails.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
class SendingEmailController {

    private EmailService emailService;

    @Autowired
    public SendingEmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    private final static String ADMINISTRATOR_EMAIL = "tomek.ziola@gmail.com";

    @PostMapping("/")
    public String processSendingEmail(@ModelAttribute("newMessage") Message messageToBeSent) {
        String message = messageToBeSent.getMessage() + " SENT BY: " + messageToBeSent.getEmail();
        emailService.sendSimpleMessage(ADMINISTRATOR_EMAIL, messageToBeSent.getName(), message);
        return "redirect:/";
    }
}

