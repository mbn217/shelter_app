package com.ziola.shelter.security.token.repository;

import com.ziola.shelter.security.token.domain.VerificationToken;
import com.ziola.shelter.workers.domain.Worker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<VerificationToken, Integer> {

    VerificationToken findByToken(String token);

    VerificationToken findByWorker(Worker worker);
}
