package com.dflex.ircs.portal.auth.service;

import java.util.Optional;

import com.dflex.ircs.portal.auth.entity.UserCategory;


/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface UserCategoryService {
	
	public Optional<UserCategory> findById(Long id);
	
	public UserCategory saveUserCategory(UserCategory userCategory);
	
	public UserCategory findByUserCategoryCodeAndRecordStatusId(
			String userCategoryCode, Long recordStatusId);
		
}