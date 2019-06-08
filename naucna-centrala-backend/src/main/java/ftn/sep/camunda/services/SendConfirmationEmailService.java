package ftn.sep.camunda.services;

import java.util.UUID;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.sep.model.User;
import ftn.sep.model.VerificationToken;
import ftn.sep.repositories.UserRepository;
import ftn.sep.repositories.VerificationTokenRepository;

@Service
public class SendConfirmationEmailService implements JavaDelegate{
	
	@Autowired
	private VerificationTokenRepository tokenRepository;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void execute(DelegateExecution execution) {
		Long userId = (Long) execution.getVariable("userId");
		User user = this.userRepository.findById(userId).get();
		/*VerificationToken token = this.generateToken(author);
		String text = constructEmailText(execution.getProcessInstanceId(), token);*/
		String text = constructEmailText(execution.getProcessInstanceId());
		this.mailService.sendMail(user.getEmail(), "Confirm email", text);
	}
	
	public VerificationToken generateToken(ftn.sep.model.User user) {
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken(token, user);
		this.tokenRepository.save(verificationToken);
		return verificationToken;
	}
	
	public String constructEmailText(String piId) {
		String text = "Press on link to confirm email: \r\n"
				+ "http://localhost:4200/confirmEmail/" + piId;
		return text;
	}
	
	public String constructEmailText(String piId, VerificationToken token) {
		String text = "Press on link to confirm email: \r\n"
				+ "http://localhost:4200/confirmEmail/" + piId + "/" + token.getToken();
		return text;
	}

}
