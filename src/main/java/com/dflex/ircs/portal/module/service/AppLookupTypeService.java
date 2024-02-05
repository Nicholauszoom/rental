package com.dflex.ircs.portal.module.service;

import java.util.Optional;

import com.dflex.ircs.portal.module.entity.AppLookupType;

/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface AppLookupTypeService {
	
	public Optional<AppLookupType> findById(Long id);
	
	public AppLookupType saveAppLookupType(AppLookupType appLookupType);
	
}