package com.ziola.shelter.emails.service;

public interface EmailService {
	void sendSimpleMessage(String from, String subject, String text);
}