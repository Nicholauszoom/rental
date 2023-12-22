package com.ircs.portal.setup.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.ircs.portal.setup.entity.User;


/**
 * 
 * @author Augustino Mwageni
 *
 */
public interface UserService {
	
	public Optional<User> findByUserName(String userName);
	
	public Optional<User> findById(UUID userId);
	
	public User saveUser(User user);
	
	public User findByNationalIdentityNumber(String nationalIdentityNumber);
	
	public boolean isUserLocked(String userName);

	public List<User> findByUserNameOrEmailAddressOrMobileNumber(String userName, String emailAddress,
			String mobileNumber);

	public List<User> findAll();
	
	public List<User> findAllByRoleId(Long roleId);

	public List<User> getByUserName(String userName);
	
	public List<User> getByEmailAddressOrMobileNumberAndNotUserName(String emailAddress,String mobileNumber,String userName);
}
