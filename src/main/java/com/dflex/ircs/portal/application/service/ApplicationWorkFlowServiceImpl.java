package com.dflex.ircs.portal.application.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.application.entity.ApplicationWorkFlow;
import com.dflex.ircs.portal.application.repository.ApplicationWorkFlowRepository;

/**
 * 
 * @author Augustino Mwageni
 *
 */
@Service
public class ApplicationWorkFlowServiceImpl implements ApplicationWorkFlowService {
	
	@Autowired
	private ApplicationWorkFlowRepository applicationWorkFlowRepository;

	@Override
	public Optional<ApplicationWorkFlow> findById(Long id) {
		return applicationWorkFlowRepository.findById(id);
	}

	@Override
	public ApplicationWorkFlow saveApplicationWorkFlow(ApplicationWorkFlow applicationWorkFlow) {
		return applicationWorkFlowRepository.save(applicationWorkFlow);
	}

	@Override
	public List<ApplicationWorkFlow> findByApplicationUidAndRecordStatusId(UUID applicationUid, Long recordStatusId) {
		return applicationWorkFlowRepository.findByApplicationUidAndRecordStatusId(applicationUid,recordStatusId);
	}

}