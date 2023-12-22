package com.ircs.portal.setup.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ircs.portal.setup.entity.User;


public interface UserRepository extends JpaRepository<User, UUID> {

	@Query("from User u where (u.userName =:userName or u.nationalIdentityNumber =:userName)")
	public Optional<User> findByUserName(String userName);

	public List<User> findByUserNameOrEmailAddressOrMobileNumber(String userName, String emailAddress,
			String mobileNumber);
	
	public User findByNationalIdentityNumber(String nationalIdentityNumber);

	@Query("from User u where upper(u.userName) =:userName ")
	public List<User> getByUserName(String userName);
	
	@Query("from User u where (upper(u.emailAddress) =:emailAddress or u.mobileNumber =:mobileNumber) and upper(u.userName) !=:userName ")
	public List<User> getByEmailAddressOrMobileNumberAndNotUserName(String emailAddress,String mobileNumber,String userName);

}
