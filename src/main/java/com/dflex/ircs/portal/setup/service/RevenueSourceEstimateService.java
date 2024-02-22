package com.dflex.ircs.portal.setup.service;

import java.util.List;
import java.util.Optional;

import com.dflex.ircs.portal.setup.entity.RevenueSourceEstimate;


public interface RevenueSourceEstimateService {

    public Optional<RevenueSourceEstimate> findById(Long id);

	public RevenueSourceEstimate saveRevenueSourceEstimate(RevenueSourceEstimate revenueSourceEstimate);

	public List<RevenueSourceEstimate> findByServiceInstitutionIdAndFinacialYearIdAndRecordStatusId(Long serviceInstitutionId,
			Long financialYearId,Long recordStatusId);

	public RevenueSourceEstimate findByRevenueSourceIdAndFinancialYearIdAndRecordStatusId(Long revenueSourceId,
			Long financialYearId, Long recordStatusId);
}
