package com.dflex.ircs.portal.application.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.dflex.ircs.portal.application.entity.ApplicationWorkFlow;

/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface ApplicationWorkFlowService {
	
	public Optional<ApplicationWorkFlow> findById(Long id);
	
	public ApplicationWorkFlow saveApplicationWorkFlow(ApplicationWorkFlow applicationWorkFlow);
	
	public List<ApplicationWorkFlow> findByApplicationUidAndRecordStatusId(UUID applicationUid,Long recordStatusId);
	
}