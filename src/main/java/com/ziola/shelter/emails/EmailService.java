package com.ziola.shelter.emails;

public interface EmailService {
	void sendSimpleMessage(String from, String subject, String text);
}