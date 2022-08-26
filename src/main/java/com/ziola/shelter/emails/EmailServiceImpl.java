package com.ziola.shelter.emails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
public class EmailServiceImpl implements EmailService {

  private final JavaMailSender emailSender;

  @Autowired
  public EmailServiceImpl(@Qualifier("customEmailSender") JavaMailSender emailSender) {
    this.emailSender = emailSender;
  }

  @Override
  public void sendSimpleMessage(String from, String subject, String text) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(from);
    message.setSubject(subject + " -> " + from);
    message.setText(text);
    emailSender.send(message);
  }
}
