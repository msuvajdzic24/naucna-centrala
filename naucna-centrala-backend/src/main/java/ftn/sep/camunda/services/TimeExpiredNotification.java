package ftn.sep.camunda.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.sep.model.User;
import ftn.sep.repositories.UserRepository;

@Service
public class TimeExpiredNotification implements JavaDelegate {
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String authorUsername = (String) execution.getVariable("author");
		User user = this.userRepository.findByUsername(authorUsername).get();
		String text = "One of task didn't finish on time."; 
		this.mailService.sendMail(user.getEmail(), "Time expired", text);
		
	}

}
