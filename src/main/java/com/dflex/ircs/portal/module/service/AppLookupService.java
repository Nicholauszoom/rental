package com.dflex.ircs.portal.module.service;

import java.util.List;
import java.util.Optional;

import com.dflex.ircs.portal.module.entity.AppLookup;

/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface AppLookupService {
	
	public Optional<AppLookup> findById(Long id);
	
	public AppLookup saveAppLookup(AppLookup appLookup);
	
	public List<AppLookup> findByAppLookupTypeIdAndRecordStatusIdOrderByLookupKeyAsc(Long appLookupTypeId,Long recordStatusId);

}