package com.ircs.portal.setup.service;

import java.util.List;
import java.util.Optional;

import com.ircs.portal.setup.entity.ServiceInstitutionCategory;

/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface ServiceInstitutionCategoryService {
	
	public Optional<ServiceInstitutionCategory> findById(Long id);
	
	public ServiceInstitutionCategory saveServiceInstitutionCategory(ServiceInstitutionCategory serviceInstitutionCategory);
	
	public List<ServiceInstitutionCategory> findAll();
	
}