package com.dflex.ircs.portal.module.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.module.entity.AppLookupType;
import com.dflex.ircs.portal.module.repository.AppLookupTypeRepository;

/**
 * 
 * @author Augustino Mwageni
 *
 */
@Service
public class AppLookupTypeServiceImpl implements AppLookupTypeService {
	
	@Autowired
	private AppLookupTypeRepository appLookupTypeRepository;

	@Override
	public Optional<AppLookupType> findById(Long id) {
		return appLookupTypeRepository.findById(id);
	}

	@Override
	public AppLookupType saveAppLookupType(AppLookupType appLookupType) {
		return appLookupTypeRepository.save(appLookupType);
	}

	
}