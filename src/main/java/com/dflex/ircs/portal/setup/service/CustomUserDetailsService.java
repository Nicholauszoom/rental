package com.dflex.ircs.portal.setup.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import com.dflex.ircs.portal.setup.entity.User;
import com.dflex.ircs.portal.setup.entity.UserPrincipal;
import com.dflex.ircs.portal.setup.repository.UserRepository;
import com.dflex.ircs.portal.util.Constants;

import jakarta.annotation.PostConstruct;


@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
    private WebApplicationContext applicationContext;
    private UserRepository userRepository;

    public CustomUserDetailsService() {
        super();
    }

    @PostConstruct
    public void completeSetup() {
        userRepository = applicationContext.getBean(UserRepository.class);
    }

	@Override
	public UserDetails loadUserByUsername(final String username) {
		System.out.println("username*****************" + username);
		final Optional<User> user = userRepository.findByUserName(username);
		System.out.println("username*****************" + user.get());
		setUsername(username);
		if (!user.isPresent()) {
			throw new UsernameNotFoundException(username);
		}
		
		if(user.get().getLastFailedLogin() != null) {
			LocalDateTime now = LocalDateTime.now();
    		LocalDateTime lastFailedLogin = user.get().getLastFailedLogin().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
    	
    		Duration timelocked = Duration.between(lastFailedLogin, now);
    		if(!user.get().getIsAccountNonLocked()) {
    			
    			if(timelocked.getSeconds() > Constants.USER_ACCOUNT_AUTOLOCK_TIME) {
        			unlockAccount(user.get());
        		}
    		}
		}
		
		UserPrincipal userPrincipal = new UserPrincipal(user.get());
		System.out.println("userPrincipal*****************" + userPrincipal);
		return userPrincipal;
	}
   
    private String username =null;
    private void setUsername(String username) {
    	this.username = username;
    }
    
    public String getUsername() {
    	return this.username;
    }
    
    private void unlockAccount(User user) {
		user.setIsAccountNonLocked(true);
		user.setLoginAttemptCount(0);
		user.setLastFailedLogin(null);
		userRepository.save(user);
	}
    
}
