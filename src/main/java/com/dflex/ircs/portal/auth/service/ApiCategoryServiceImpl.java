package com.dflex.ircs.portal.auth.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.auth.entity.ApiCategory;
import com.dflex.ircs.portal.auth.repository.ApiCategoryRepository;



/**
 * 
 * @author Augustino Mwageni
 *
 */

@Service
public class ApiCategoryServiceImpl implements ApiCategoryService {

	@Autowired
	private ApiCategoryRepository  apiCategoryRepository;

	@Override
	public Optional<ApiCategory> findById(Long id) {
		return apiCategoryRepository.findById(id);
	}

	@Override
	public List<ApiCategory> findAll() {
		return apiCategoryRepository.findAll();
	}

	@Override
	public ApiCategory createApiCategory(ApiCategory apiCategory) {
		return apiCategoryRepository.save(apiCategory);
	}

	@Override
	public ApiCategory updateApiCategory(ApiCategory apiCategory) {
		return apiCategoryRepository.save(apiCategory);
	}

}
