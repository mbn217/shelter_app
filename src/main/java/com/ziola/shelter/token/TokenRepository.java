package com.ziola.shelter.token;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<VerificationToken, Integer> {

    VerificationToken findByToken(String token);
}
