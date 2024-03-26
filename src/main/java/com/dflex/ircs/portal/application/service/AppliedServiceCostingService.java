package com.dflex.ircs.portal.application.service;

import java.util.List;
import java.util.Optional;

import com.dflex.ircs.portal.application.entity.AppliedServiceCosting;

/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface AppliedServiceCostingService {
	
	public Optional<AppliedServiceCosting> findById(Long id);
	
	public AppliedServiceCosting saveAppliedServiceCosting(AppliedServiceCosting appliedServiceCosting);
	
	public List<AppliedServiceCosting> findByReferenceApplicationIdAndReferenceApplicationTableAndRecordStatusId(
			Long referenceApplicationId,String referenceApplicationTable,Long recordStatusId);
	
}