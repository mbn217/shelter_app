package com.ziola.shelter.token;

import com.ziola.shelter.workers.Worker;
import org.springframework.ui.Model;

import java.util.Locale;

public interface VerificationTokenService {
    boolean checkIfTokenIsExpired(Model model, Locale locale, VerificationToken verificationToken);

    void createVerificationToken(Worker worker, String token);

    VerificationToken getVerificationToken(String token);

    void deleteTokenByWorkerId(int id);
}
