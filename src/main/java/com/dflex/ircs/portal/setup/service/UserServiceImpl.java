package com.dflex.ircs.portal.setup.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.setup.entity.Role;
import com.dflex.ircs.portal.setup.entity.User;
import com.dflex.ircs.portal.setup.repository.UserRepository;
import com.dflex.ircs.portal.util.Constants;


/**
 * 
 * @author Augustino Mwageni
 *
 */
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public Optional<User> findByUserName(String userName) {
		return userRepository.findByUserName(userName);
	}

	@Override
	public Optional<User> findById(UUID userId) {
		return userRepository.findById(userId);
	}

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}
	
	public User findByNationalIdentityNumber(String nationalIdentityNumber){
		return userRepository.findByNationalIdentityNumber(nationalIdentityNumber);
	}

	@Override
	public boolean isUserLocked(String userName) {
		Integer loginAttempts = 0;

		Optional<User> user = userRepository.findByUserName(userName);
		loginAttempts = user.get().getLoginAttemptCount() + 1;

		user.get().setLoginAttemptCount(loginAttempts);
		user.get().setLastFailedLogin(new Date());
		try {

			if (loginAttempts > Constants.MAX_LOGIN_ATTEMPTS) {
				user.get().setIsAccountNonLocked(true);
				userRepository.save(user.get());
				return true;
			} else {
				userRepository.save(user.get());
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<User> findByUserNameOrEmailAddressOrMobileNumber(String userName, String emailAddress,
			String mobileNumber) {
		return userRepository.findByUserNameOrEmailAddressOrMobileNumber(userName,emailAddress,mobileNumber);
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public List<User> findAllByRoleId(Long roleId) {
		
		List<User> users = findAll();
		List<User> roleUsers = new ArrayList<>();
		
		if(users != null && !users.isEmpty()) {
			
			for(User user : users) {
				
				if(user.getRole() != null && !user.getRole().isEmpty()) {
					
					for (Role role : user.getRole()) {
						if(role.getId().equals(roleId)) {
							roleUsers.add(user);
						}
					}
				}
			}
		}
		
		return roleUsers;
	}

	@Override
	public List<User> getByUserName(String userName) {
		return userRepository.getByUserName(userName);
	}

	@Override
	public List<User> getByEmailAddressOrMobileNumberAndNotUserName(String emailAddress,String mobileNumber,String userName){
		return userRepository.getByEmailAddressOrMobileNumberAndNotUserName(emailAddress,mobileNumber,userName);
	}

}
