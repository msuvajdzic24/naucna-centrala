package ftn.sep.camunda.services;

import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ftn.sep.dto.DTOFormSubmission;
import ftn.sep.exceptions.NotFoundException;
import ftn.sep.model.Author;
import ftn.sep.model.Role;
import ftn.sep.model.VerificationToken;
import ftn.sep.repositories.AuthorRepository;
import ftn.sep.repositories.RoleRepository;
import ftn.sep.repositories.UserRepository;
import ftn.sep.repositories.VerificationTokenRepository;

@Service
public class RegistrationService implements JavaDelegate {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthorRepository authorRepository;
	
	@Autowired
	private RoleRepository RoleRepository;
	
	@Autowired
	private VerificationTokenRepository tokenRepository;

	@Override
	public void execute(DelegateExecution execution) throws Exception {
				
		Author author = new Author(); 
	    author.setUsername((String) execution.getVariable("username"));
	    String password = (String) execution.getVariable("password");
	    String encodedPassword = passwordEncoder.encode(password);
	    author.setPassword(encodedPassword);
	    author.setEmail((String) execution.getVariable("email"));
	    author.setName((String) execution.getVariable("name"));
	    author.setSurname((String) execution.getVariable("surname"));
	    author.setCity((String) execution.getVariable("city"));
	    author.setCountry((String) execution.getVariable("country"));
	
	    // samo autor moze da se registruje trenutno
		Role role = this.RoleRepository.findByName("author");
		author.getRoles().add(role);
		author.setActivated(false);
		this.authorRepository.save(author);
		execution.setVariable("userId", author.getId());
	}
	
	public boolean userExist(List<DTOFormSubmission> dtoFS) {
		for (DTOFormSubmission formField : dtoFS) {
			if (formField.getFieldId().equals("username")) {
				if (this.userRepository.findByUsername(formField.getFieldValue()).orElse(null) != null) {
					return true;
				}
				break;
			}
		}
		return false;
	}
	
	public void confirmEmail(String token) {
		VerificationToken verificationToken = this.tokenRepository.findByToken(token);
		if (verificationToken == null) {
			throw new NotFoundException("Token not found!");
		}
		
		ftn.sep.model.User user = this.userRepository.findById(verificationToken.getUser().getId()).orElse(null);
		if (user == null) {
			throw new NotFoundException("User not found!");
		}
		user.setActivated(true);
		this.userRepository.save(user);
		
        this.tokenRepository.delete(verificationToken);
	}
	
	public void confirmEmail(Long authorId) {
		Author author = this.authorRepository.findById(authorId).orElse(null);
		if (author == null) {
			throw new NotFoundException("User not found!");
		}
		author.setActivated(true);
		this.authorRepository.save(author);
	}

}
