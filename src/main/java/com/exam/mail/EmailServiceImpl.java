package com.exam.mail;

import java.io.File;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String sender;

	// Method 1
	// To send a simple email
	public String sendSimpleMail(EmailDetails details) {

		// Try block to check for exceptions
		try {

			// Creating a simple mail message
			SimpleMailMessage mailMessage = new SimpleMailMessage();

			// Setting up necessary details
			mailMessage.setFrom(sender);
			mailMessage.setTo(details.getRecipient());
			mailMessage.setText(details.getMsgBody());
			mailMessage.setSubject(details.getSubject());
			mailMessage.setBcc("lokeshmayekar77@gmail.com");
			mailMessage.setCc("lokeshmayekar77@gmail.com");
		
			// Sending the mail
			javaMailSender.send(mailMessage);
			return "Mail Sent Successfully...";
		}

		// Catch block to handle the exceptions
		catch (Exception e) {
			System.out.println("Exception : " + e);
			return "Error while Sending Mail";
		}
	}

	// Method 2
	// To send an email with attachment
	public String sendMailWithAttachment(EmailDetails details) {
		// Creating a mime message
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();

		try {
			mimeMessage.setContent(details.getMsgBody(), "text/html");
		} catch (MessagingException e1) {

			e1.printStackTrace();
		}
		MimeMessageHelper mimeMessageHelper;

		try {

			// Setting multipart as true for attachments to
			// be send
			mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
			mimeMessageHelper.setFrom(sender);
			mimeMessageHelper.setTo(details.getRecipient());
			mimeMessageHelper.setText(details.getMsgBody());
			mimeMessageHelper.setSubject(details.getSubject());

			// Adding the attachment
			if (details.getAttachment() != null) {
				FileSystemResource file = new FileSystemResource(new File(details.getAttachment()));
				mimeMessageHelper.addAttachment(file.getFilename(), file);
			}
			// Sending the mail
			javaMailSender.send(mimeMessage);
			return "Mail sent Successfully";
		}

		// Catch block to handle MessagingException
		catch (MessagingException e) {

			// Display message when exception occurred
			return "Error while sending mail!!!";
		}
	}
}
