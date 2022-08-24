package com.ziola.shelter.security.controllers;

import com.ziola.shelter.exceptions.EmailExistsException;
import com.ziola.shelter.security.event.OnRegistrationCompleteEvent;
import com.ziola.shelter.security.token.domain.VerificationToken;
import com.ziola.shelter.workers.domain.Worker;
import com.ziola.shelter.workers.dto.WorkerDTO;
import com.ziola.shelter.workers.service.WorkerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Calendar;
import java.util.Locale;

@Controller
public class RegistrationController {

    private  WorkerService workerService;
    private  ApplicationEventPublisher eventPublisher;
    private  MessageSource messages;

    @Autowired
    public RegistrationController(WorkerService workerService, ApplicationEventPublisher eventPublisher, MessageSource messages) {
        this.workerService = workerService;
        this.eventPublisher = eventPublisher;
        this.messages = messages;
    }

    @GetMapping(value = "/registration")
    public String registration(Model model) {
        WorkerDTO workerDTO = new WorkerDTO();
        model.addAttribute("worker", workerDTO);
        return "registration";
    }

    @PostMapping(value = "/registration")
    public String registerNewWorker(@Valid @ModelAttribute("worker") WorkerDTO workerDTO, BindingResult result, WebRequest request, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "registration";
        }
        Worker workerExists = createWorkerAccount(workerDTO);
        if (workerExists == null) {
            result.rejectValue("email", "error.user", "Adres email jest zajęty");
        }
        try {
            String appUrl = request.getContextPath();
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(workerExists, request.getLocale(), appUrl));
        } catch (Exception me) {
            return "registration";
        }
        redirectAttributes.addFlashAttribute("messageSucces", "Rejestracja udana. Sprawdź maila.");
        return "redirect:/registration";
    }

    @GetMapping("/registrationConfirm")
    public String confirmRegistration(WebRequest request, Model model, @RequestParam("token") String token) {

        Locale locale = request.getLocale();

        VerificationToken verificationToken = workerService.getVerificationToken(token);
        if (verificationToken == null) {
            String message = messages.getMessage("auth.message.invalidToken", null, locale);
            model.addAttribute("message", message);
            return "redirect:/login";
        }
        Worker worker = verificationToken.getWorker();
        if (checkIfTokenIsExpired(model, locale, verificationToken)) return "redirect:/login";
        worker.setActive(true);
        workerService.saverRegisteredWorker(worker);
        return "redirect:/login";
    }

    private boolean checkIfTokenIsExpired(Model model, Locale locale, VerificationToken verificationToken) {
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime() <= 0)) {
            String messageValue = messages.getMessage("auth.message.expired", null, locale);
            model.addAttribute("message", messageValue);
            return true;
        }
        return false;
    }

    private Worker createWorkerAccount(WorkerDTO workerDTO){
        Worker registered;
        try {
            registered = workerService.registerNewUserAccount(workerDTO);
        } catch (EmailExistsException emailExistsException) {
            return null;
        }
        return registered;
    }
}
