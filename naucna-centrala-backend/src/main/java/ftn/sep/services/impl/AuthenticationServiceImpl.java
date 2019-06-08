package ftn.sep.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ftn.sep.dto.DTOLogin;
import ftn.sep.model.Role;
import ftn.sep.model.User;
import ftn.sep.repositories.UserRepository;
import ftn.sep.services.AuthenticationService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean loginUser(DTOLogin dtoLogin) {
		return userRepository.findByUsername(dtoLogin.getUsername()) != null;
	}

	@Override
	public Role getUserRole(String username) {
		User u = this.userRepository.findByUsername(username).get();
		Role role = null;
		for (Role r : u.getRoles()) {
			role = r;
		}
		return role;
	}

}
