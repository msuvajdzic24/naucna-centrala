package ftn.sep.services;

import ftn.sep.dto.DTOLogin;
import ftn.sep.model.Role;

public interface AuthenticationService {
	
	public boolean loginUser(DTOLogin dtoLogin);
	public Role getUserRole(String username);

}
