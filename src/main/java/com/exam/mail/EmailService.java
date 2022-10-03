package com.exam.mail;

public interface EmailService {

	String sendSimpleMail(EmailDetails details);
	String sendMailWithAttachment(EmailDetails details);

}
