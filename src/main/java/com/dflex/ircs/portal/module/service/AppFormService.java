package com.dflex.ircs.portal.module.service;

import java.util.Optional;
import java.util.UUID;

import com.dflex.ircs.portal.module.entity.AppForm;

/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface AppFormService {
	
	public Optional<AppForm> findById(Long id);
	
	public AppForm saveAppForm(AppForm appForm);
	
	public AppForm findByAppFormUidAndRecordStatusId(UUID appFormUid,Long recordStatusId);
	
}