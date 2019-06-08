package ftn.sep.controllers;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ftn.sep.dto.DTOLogin;
import ftn.sep.dtoreturn.DTOToken;
import ftn.sep.exceptions.BadRequestException;
import ftn.sep.security.TokenUtils;
import ftn.sep.services.AuthenticationService;

@RestController
public class AuthenticationController {
	
	@Value("${token.header}")
    private String tokenHeader;
	
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    private AuthenticationService authenticationService;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private TokenUtils tokenUtils;
    
	@RequestMapping(value = "/login", 
			method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> loginUser(@RequestBody @Valid DTOLogin login, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return new ResponseEntity<String>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.FORBIDDEN);
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (!(auth instanceof AnonymousAuthenticationToken)) {
			throw new BadRequestException("Unauthorized!"); 
		}

		try {
			if (this.authenticationService.loginUser(login)) {
				
				HttpHeaders hh = new HttpHeaders();
				
				DTOToken token = new DTOToken();
				UserDetails userDetails = this.userDetailsService.loadUserByUsername(login.getUsername());
				String tokenValue = this.tokenUtils.generateToken(userDetails, hh);
				token.setToken(tokenValue);
				
				Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));

				SecurityContextHolder.getContext().setAuthentication(authentication);
				
				return new ResponseEntity<DTOToken>(token, hh, HttpStatus.OK);
			}
			
			return null;
			
		} catch (UnsupportedEncodingException e) {
			throw new BadRequestException("Error occured!"); 
        } catch (NoSuchAlgorithmException e) {
        	throw new BadRequestException("Error occured!"); 
        }

	}
    
	@RequestMapping(value = "/logoutUser",
            method = RequestMethod.GET,
            produces = MediaType.TEXT_PLAIN_VALUE)
    public ResponseEntity<String> logoutUser(@RequestHeader(value = "X-Auth-Token", required = false) String token) throws NoSuchAlgorithmException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        if (!(auth instanceof AnonymousAuthenticationToken)){
            SecurityContextHolder.clearContext();
            return new ResponseEntity<String>("Successful logout!", HttpStatus.OK);
        } else {
        	throw new BadRequestException("User is not authenticated!");
        }

    }

}
