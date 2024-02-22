package com.dflex.ircs.portal.setup.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.setup.entity.RevenueSourceEstimate;
import com.dflex.ircs.portal.setup.repository.RevenueSourceEstimateRepository;



@Service
public class RevenueSourceEstimateServiceImpl implements RevenueSourceEstimateService {
	
	@Autowired
	private RevenueSourceEstimateRepository revenueSourceEstimateRepository;

	@Override
	public Optional<RevenueSourceEstimate> findById(Long id) {
		return revenueSourceEstimateRepository.findById(id);
	}

	@Override
	public RevenueSourceEstimate saveRevenueSourceEstimate(RevenueSourceEstimate revenueSourceEstimate) {
		return revenueSourceEstimateRepository.save(revenueSourceEstimate);
	}

	@Override
	public List<RevenueSourceEstimate> findByServiceInstitutionIdAndFinacialYearIdAndRecordStatusId(Long serviceInstitutionId,
			Long financialYearId,Long recordStatusId) {
		return revenueSourceEstimateRepository.findByServiceInstitutionIdAndFinacialYearIdAndRecordStatusId(serviceInstitutionId,
				financialYearId,recordStatusId);
	}

	@Override
	public RevenueSourceEstimate findByRevenueSourceIdAndFinancialYearIdAndRecordStatusId(Long revenueSourceId,
			Long financialYearId, Long recordStatusId) {
		return revenueSourceEstimateRepository.findByRevenueSourceIdAndFinancialYearIdAndRecordStatusId(revenueSourceId,
				financialYearId,recordStatusId);
	}

	
}