package ftn.sep.camunda.services;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.sep.model.User;
import ftn.sep.repositories.UserRepository;

@Service
public class DeleteUserService implements JavaDelegate {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		Long userId = (Long) execution.getVariable("userId");
		User user = this.userRepository.findById(userId).get();
		
		this.userRepository.delete(user);
	}

}
