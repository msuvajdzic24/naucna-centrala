package ftn.sep.camunda.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	private Environment env;
	
	@Async
	public void sendMail(String to, String subject, String text) {
        final SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(to);
        email.setSubject(subject);
        email.setText(text);
        email.setFrom(env.getProperty("support.email"));
       
        this.mailSender.send(email);
		
		System.out.println("POSLAT MAIL");
		System.out.println("To: " + to);
		System.out.println("Subject: " + subject);
		System.out.println("Text: " + text);
		
        return;
	}
}
