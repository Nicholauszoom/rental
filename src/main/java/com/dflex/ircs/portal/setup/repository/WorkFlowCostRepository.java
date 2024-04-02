package com.dflex.ircs.portal.setup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dflex.ircs.portal.setup.entity.WorkFlowCost;


public interface WorkFlowCostRepository extends JpaRepository<WorkFlowCost, Long> {

	@Query("from WorkFlowCost w where w.revenueSourceWorkFlow.revenueSourceId =:revenueSourceId "
			+ " and w.revenueSourceWorkFlow.workFlowId =:workFlowId and w.recordStatusId =:recordStatusId")
	public List<WorkFlowCost> findByRevenueSourceIdAndWorkFlowIdAndRecordStatusId(Long revenueSourceId,Long workFlowId, Long recordStatusId);

}
