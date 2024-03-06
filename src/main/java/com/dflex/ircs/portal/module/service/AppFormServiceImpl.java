package com.dflex.ircs.portal.module.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.module.entity.AppForm;
import com.dflex.ircs.portal.module.repository.AppFormRepository;

/**
 * 
 * @author Augustino Mwageni
 *
 */
@Service
public class AppFormServiceImpl implements AppFormService {
	
	@Autowired
	private AppFormRepository appFormRepository;

	@Override
	public Optional<AppForm> findById(Long id) {
		return appFormRepository.findById(id);
	}

	@Override
	public AppForm saveAppForm(AppForm appForm) {
		return appFormRepository.save(appForm);
	}

	@Override
	public AppForm findByAppFormUidAndRecordStatusId(UUID appFormUid, Long recordStatusId) {
		return appFormRepository.findByAppFormUidAndRecordStatusId(appFormUid,recordStatusId);
	}

}