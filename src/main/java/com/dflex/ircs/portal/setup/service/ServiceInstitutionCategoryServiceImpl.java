package com.dflex.ircs.portal.setup.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.setup.entity.ServiceInstitutionCategory;
import com.dflex.ircs.portal.setup.repository.ServiceInstitutionCategoryRepository;


/**
 * 
 * @author Augustino Mwageni
 *
 */

@Service
public class ServiceInstitutionCategoryServiceImpl implements ServiceInstitutionCategoryService{
	
	@Autowired
	private ServiceInstitutionCategoryRepository serviceInstitutionCategoryRepository;

	@Override
	public Optional<ServiceInstitutionCategory> findById(Long id) {
		return serviceInstitutionCategoryRepository.findById(id);
	}

	@Override
	public ServiceInstitutionCategory saveServiceInstitutionCategory(ServiceInstitutionCategory serviceInstitutionCategory) {
		return serviceInstitutionCategoryRepository.save(serviceInstitutionCategory);
	}

	@Override
	public List<ServiceInstitutionCategory> findAll() {
		return serviceInstitutionCategoryRepository.findAll();
	}
}