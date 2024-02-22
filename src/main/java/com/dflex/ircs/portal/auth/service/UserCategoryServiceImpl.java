package com.dflex.ircs.portal.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.auth.entity.UserCategory;
import com.dflex.ircs.portal.auth.repository.UserCategoryRepository;


/**
 * 
 * @author Augustino Mwageni
 *
 */

@Service
public class UserCategoryServiceImpl implements UserCategoryService{
	
	@Autowired
	private UserCategoryRepository userCategoryRepository;

	@Override
	public Optional<UserCategory> findById(Long id) {
		return userCategoryRepository.findById(id);
	}

	@Override
	public UserCategory saveUserCategory(UserCategory userCategory) {
		return userCategoryRepository.save(userCategory);
	}

	@Override
	public UserCategory findByUserCategoryCodeAndRecordStatusId(String userCategoryCode,Long recordStatusId) {
		return userCategoryRepository.findByUserCategoryCodeAndRecordStatusId(userCategoryCode,recordStatusId);
	}
}