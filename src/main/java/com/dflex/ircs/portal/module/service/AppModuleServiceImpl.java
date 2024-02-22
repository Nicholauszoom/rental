package com.dflex.ircs.portal.module.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.module.entity.AppModule;
import com.dflex.ircs.portal.module.repository.AppModuleRepository;

/**
 * 
 * @author Augustino Mwageni
 *
 */
@Service
public class AppModuleServiceImpl implements AppModuleService {
	
	@Autowired
	private AppModuleRepository appModuleRepository;

	@Override
	public Optional<AppModule> findById(Long id) {
		return appModuleRepository.findById(id);
	}

	@Override
	public AppModule saveAppModule(AppModule appModule) {
		return appModuleRepository.save(appModule);
	}

	@Override
	public List<AppModule> findByRecordStatusId(Long recordStatusId) {
		return appModuleRepository.findByRecordStatusId(recordStatusId);
	}

	@Override
	public List<AppModule> findAll() {
		return appModuleRepository.findAll();
	}
	
}