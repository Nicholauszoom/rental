package com.dflex.ircs.portal.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.application.entity.AppliedServiceCosting;
import com.dflex.ircs.portal.application.repository.AppliedServiceCostingRepository;

/**
 * 
 * @author Augustino Mwageni
 *
 */
@Service
public class AppliedServiceCostingServiceImpl implements AppliedServiceCostingService {
	
	@Autowired
	private AppliedServiceCostingRepository appliedServiceCostingRepository;

	@Override
	public Optional<AppliedServiceCosting> findById(Long id) {
		return appliedServiceCostingRepository.findById(id);
	}

	@Override
	public AppliedServiceCosting saveAppliedServiceCosting(AppliedServiceCosting appliedServiceCosting) {
		return appliedServiceCostingRepository.save(appliedServiceCosting);
	}

	@Override
	public List<AppliedServiceCosting> findByReferenceApplicationIdAndReferenceApplicationTableAndRecordStatusId(
			Long referenceApplicationId, String referenceApplicationTable, Long recordStatusId) {
		return appliedServiceCostingRepository.findByReferenceApplicationIdAndReferenceApplicationTableAndRecordStatusId(
				referenceApplicationId, referenceApplicationTable, recordStatusId);
	}

}