package com.ziola.shelter.token;

import com.ziola.shelter.workers.Worker;
import com.ziola.shelter.workers.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Calendar;
import java.util.Locale;
@RequiredArgsConstructor
@Service
public class VerificationTokenServiceImpl implements VerificationTokenService{

    private final MessageSource messages;
    private final TokenRepository tokenRepository;

    private final WorkerRepository workerRepository;

    @Override
    public boolean checkIfTokenIsExpired(Model model, Locale locale, VerificationToken verificationToken) {
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime() <= 0)) {
            String messageValue = messages.getMessage("auth.message.expired", null, locale);
            model.addAttribute("message", messageValue);
            return true;
        }
        return false;
    }

    @Override
    public void createVerificationToken(Worker worker, String token) {
        VerificationToken myToken = new VerificationToken(token, worker);
        tokenRepository.save(myToken);
    }

    @Override
    public VerificationToken getVerificationToken(String token) {
        return tokenRepository.findByToken(token);
    }

    @Override
    public void deleteTokenByWorkerId(int id) {
        Worker worker = workerRepository.findById(id);
        tokenRepository.delete(worker.getVerificationToken());
    }
}
