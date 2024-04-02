package com.dflex.ircs.portal.setup.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.setup.entity.WorkFlowCost;
import com.dflex.ircs.portal.setup.repository.WorkFlowCostRepository;


/**
 * 
 * @author Augustino Mwageni
 *
 */

@Service
public class WorkFlowCostServiceImpl implements WorkFlowCostService{
	
	@Autowired
	private WorkFlowCostRepository workFlowCostRepository;

	@Override
	public Optional<WorkFlowCost> findById(Long id) {
		return workFlowCostRepository.findById(id);
	}

	@Override
	public WorkFlowCost saveWorkFlowCost(WorkFlowCost workFlowCost) {
		return workFlowCostRepository.save(workFlowCost);
	}

	@Override
	public List<WorkFlowCost> findByRevenueSourceIdAndWorkFlowIdAndRecordStatusId(Long revenueSourceId,Long workFlowId, Long recordStatusId){
		return workFlowCostRepository.findByRevenueSourceIdAndWorkFlowIdAndRecordStatusId(revenueSourceId,
				workFlowId,recordStatusId);
	}

	
}