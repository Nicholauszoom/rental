package com.dflex.ircs.portal.module.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.module.entity.AppLookup;
import com.dflex.ircs.portal.module.repository.AppLookupRepository;

/**
 * 
 * @author Augustino Mwageni
 *
 */
@Service
public class AppLookupServiceImpl implements AppLookupService {
	
	@Autowired
	private AppLookupRepository appLookupRepository;

	@Override
	public Optional<AppLookup> findById(Long id) {
		return appLookupRepository.findById(id);
	}

	@Override
	public AppLookup saveAppLookup(AppLookup appLookup) {
		return appLookupRepository.save(appLookup);
	}

	@Override
	public List<AppLookup> findByAppLookupTypeIdAndRecordStatusIdOrderByLookupKeyAsc(Long appLookupTypeId, Long recordStatusId) {
		return appLookupRepository.findByAppLookupTypeIdAndRecordStatusIdOrderByLookupKeyAsc(appLookupTypeId,recordStatusId);
	}
	
}