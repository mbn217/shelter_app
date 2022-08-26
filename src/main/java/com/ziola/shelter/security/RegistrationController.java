package com.ziola.shelter.security;

import com.ziola.shelter.token.VerificationToken;
import com.ziola.shelter.token.VerificationTokenService;
import com.ziola.shelter.workers.Worker;
import com.ziola.shelter.workers.WorkerDTO;
import com.ziola.shelter.workers.WorkerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

import java.util.Locale;

@RequiredArgsConstructor
@Controller
public class RegistrationController {

    private final WorkerService workerService;
    private final ApplicationEventPublisher eventPublisher;
    private final MessageSource messages;
    private final VerificationTokenService verificationTokenService;


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
        Worker workerExists = workerService.createWorkerAccount(workerDTO);
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
        if (verificationTokenService.checkIfTokenIsExpired(model, locale, verificationToken)) return "redirect:/login";
        worker.setActive(true);
        workerService.saverRegisteredWorker(worker);
        return "redirect:/login";
    }
}
