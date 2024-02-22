package com.dflex.ircs.portal.setup.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.setup.entity.WorkFlow;
import com.dflex.ircs.portal.setup.repository.WorkFlowRepository;



@Service
public class WorkFlowServiceImpl implements WorkFlowService {
	
	@Autowired
	private WorkFlowRepository workFlowRepository;

	@Override
	public Optional<WorkFlow> findById(Long id) {
		return workFlowRepository.findById(id);
	}

	@Override
	public WorkFlow saveWorkFlow(WorkFlow workFlow) {
		return workFlowRepository.save(workFlow);
	}

}