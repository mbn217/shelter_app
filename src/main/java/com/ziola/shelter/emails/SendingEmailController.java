package com.ziola.shelter.emails;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
class SendingEmailController {

    private final EmailService emailService;

    private final static String ADMINISTRATOR_EMAIL = "tomek.ziola@gmail.com";

    @PostMapping("/")
    public String processSendingEmail(@ModelAttribute("newMessage") Message messageToBeSent) {
        String message = messageToBeSent.getMessage() + " SENT BY: " + messageToBeSent.getEmail();
        emailService.sendSimpleMessage(ADMINISTRATOR_EMAIL, messageToBeSent.getName(), message);
        return "redirect:/";
    }
}

