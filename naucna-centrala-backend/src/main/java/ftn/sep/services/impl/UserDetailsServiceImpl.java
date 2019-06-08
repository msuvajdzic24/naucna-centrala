package ftn.sep.services.impl;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import ftn.sep.exceptions.NotFoundException;
import ftn.sep.model.Permission;
import ftn.sep.model.Role;
import ftn.sep.model.User;
import ftn.sep.repositories.UserRepository;
import ftn.sep.security.SecurityUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
    public UserDetails loadUserByUsername(String username) {

        try {
            User user = this.userRepository.findByUsername(username).orElseThrow(()
            		-> new NotFoundException("User with that username does not exist!"));
            		
            // boolean enabled = true;
            boolean accountNonExpired = true;
            boolean credentialsNonExpired = true;
            boolean accountNonLocked = true;

            return new SecurityUser(user.getId(), user.getUsername(),
            		user.getPassword(), user.getEmail(),
                    getAuthorities(user.getRoles()), user.isActivated(), accountNonExpired,
                    credentialsNonExpired, accountNonLocked);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
	
	private static class SimpleGrantedAuthorityComparator implements Comparator<SimpleGrantedAuthority> {
		
		@Override
		public int compare(SimpleGrantedAuthority o1, SimpleGrantedAuthority o2) {
		    return o1.equals(o2) ? 0 : -1;
		}
	}
	
	public Collection<? extends GrantedAuthority> getAuthorities(Set<Role> roles) {

        Set<SimpleGrantedAuthority> authList = new TreeSet<SimpleGrantedAuthority>(new SimpleGrantedAuthorityComparator());

        for (Role role : roles) {
            authList.addAll(getGrantedAuthorities(role));
        }

        return authList;
    }
	
	public static Set<SimpleGrantedAuthority> getGrantedAuthorities(Role role) {

        Set<SimpleGrantedAuthority> authorities = new HashSet<SimpleGrantedAuthority>();

        Set<Permission> rolePermissions = role.getPermissions();
        for (Permission permission : rolePermissions) {
            authorities.add(new SimpleGrantedAuthority(permission.getName()));
        }

        return authorities;
    }
	
}
