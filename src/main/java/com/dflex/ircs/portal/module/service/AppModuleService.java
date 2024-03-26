package com.dflex.ircs.portal.module.service;

import java.util.List;
import java.util.Optional;

import com.dflex.ircs.portal.module.entity.AppModule;

/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface AppModuleService {
	
	public Optional<AppModule> findById(Long id);
	
	public AppModule saveAppModule(AppModule appModule);
	
	public List<AppModule> findByRecordStatusId(Long recordStatusId);
	
	public List<AppModule> findAll();
	
}