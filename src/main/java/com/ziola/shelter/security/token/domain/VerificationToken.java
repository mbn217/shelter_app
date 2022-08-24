package com.ziola.shelter.security.token.domain;

import com.ziola.shelter.workers.domain.Worker;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "verification_token")
public class VerificationToken {

    private static final int EXPIRATION = 60*24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Integer id;
    @Column(name = "token")
    private String token;
    @OneToOne(targetEntity = Worker.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "workers_id")
    private Worker worker;
    @Column(name = "expiry_date")
    private Date expiryDate;

    public VerificationToken(String token, Worker worker) {
        this.token = token;
        this.worker = worker;
        expiryDate = calculateExpiryDate(EXPIRATION);
    }

    private Date calculateExpiryDate(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Timestamp(cal.getTime().getTime()));
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }
}
