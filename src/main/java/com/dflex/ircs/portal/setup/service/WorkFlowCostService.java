package com.dflex.ircs.portal.setup.service;

import java.util.List;
import java.util.Optional;

import com.dflex.ircs.portal.setup.entity.WorkFlowCost;

/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface WorkFlowCostService {
	
	public Optional<WorkFlowCost> findById(Long id);

	public WorkFlowCost saveWorkFlowCost(WorkFlowCost workFlowCost);

	public List<WorkFlowCost> findByRevenueSourceIdAndWorkFlowIdAndRecordStatusId(Long revenueSourceId,Long workFlowId, Long recordStatusId);
}