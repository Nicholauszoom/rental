package com.dflex.ircs.portal.auth.service;

import java.util.List;
import java.util.Optional;

import com.dflex.ircs.portal.auth.entity.ApiCategory;



/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface ApiCategoryService {

	public Optional<ApiCategory> findById(Long id);
	
	public List<ApiCategory> findAll();
	
	public ApiCategory createApiCategory(ApiCategory apiCategory);
	
	public ApiCategory updateApiCategory(ApiCategory apiCategory);

}
