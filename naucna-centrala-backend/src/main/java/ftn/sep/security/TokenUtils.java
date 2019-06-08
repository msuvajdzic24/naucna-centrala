package ftn.sep.security;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import ftn.sep.exceptions.NotFoundException;
import ftn.sep.model.Role;
import ftn.sep.model.User;
import ftn.sep.repositories.UserRepository;

@Component
public class TokenUtils {
	
    @Value("${token.secret}")
    private String secret;

    @Value("${token.expiration}")
    private int expiration;
    
	@Autowired
	private UserRepository userRepository;
    
    @Autowired
    public TokenUtils(
            UserRepository userRepository
    ) {
        this.userRepository = userRepository;
    }
    
    public String getUsernameFromToken(String token) {
        String username;
        try {
        	DecodedJWT jwt = JWT.decode(token);
        	username = jwt.getSubject();
        } catch (Exception e){
            username = null;
        }
        return username;
    }
    
    public String generateToken(UserDetails userDetails, HttpHeaders hh) throws NoSuchAlgorithmException, UnsupportedEncodingException  {
        
        User u = this.userRepository.findByUsername(userDetails.getUsername())
        		.orElseThrow(() -> new NotFoundException("User does not exist!"));
        
        String role = "";
        List<Role> roles = new ArrayList<>(u.getRoles());
        for (int i = 0; i < roles.size(); i++) {
            if (i != roles.size()-1) {
                role = role.concat(roles.get(i).getName() + "|");
            } else {
                role = role.concat(roles.get(i).getName());
            }
        }

        Calendar c = Calendar.getInstance();
        Date now = c.getTime();
        c.add(Calendar.MINUTE, this.expiration);
        Date expirationDate = c.getTime();
        
        Map<String, Object> headerClaims = new HashMap<>();
        headerClaims.put("typ", "JWT");
        String token = JWT.create().withSubject(userDetails.getUsername())
     		   .withExpiresAt(expirationDate)
     		   .withIssuedAt(now)
     		   .withNotBefore(now)
     		   .withClaim("expiring", c.getTimeInMillis())
     		   .withClaim("roles", role)
     		   .withHeader(headerClaims)
     		   .sign(Algorithm.HMAC512(this.secret));
     
     return token;
        
    }
    
    public Boolean validateToken(HttpServletRequest request, String token, UserDetails userDetails) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    	
    	JWTVerifier verifier = JWT.require(Algorithm.HMAC512(this.secret))
    			.withSubject(userDetails.getUsername())
                .build();
    	
    	DecodedJWT decodedToken = verifier.verify(token);
    	
    	return decodedToken != null;
    }

}
