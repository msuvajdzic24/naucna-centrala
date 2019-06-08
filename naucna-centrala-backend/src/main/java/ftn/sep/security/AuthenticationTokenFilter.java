package ftn.sep.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;

public class AuthenticationTokenFilter extends UsernamePasswordAuthenticationFilter {
	
    @Value("${token.header}")
    private String tokenHeader;

    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private UserDetailsService userDetailsService;
    
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authToken = httpRequest.getHeader(this.tokenHeader);
        String username = this.tokenUtils.getUsernameFromToken(authToken);
        
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
            	UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                try {
                    if (this.tokenUtils.validateToken(httpRequest, authToken, userDetails)) {
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                    }
                } catch (AlgorithmMismatchException e) {
                    logger.error("Algorithm is not exact!");
                } catch (SignatureVerificationException e) {
                    logger.error("Token was changed and signature is not good!");
                } catch (TokenExpiredException e) {
                    logger.error("Token expired!");
                } catch (InvalidClaimException e) {
                    logger.error("Claim is invalid!");
                } catch (UnsupportedEncodingException e) {
                    logger.error("Unsupported encoding!");
                } catch (NoSuchAlgorithmException e) {
					logger.error("No such algorithm!");
				}
            } catch (UsernameNotFoundException e) {
                logger.error("Username not found");
            }
        }
        
        chain.doFilter(request, response);
    }

}
