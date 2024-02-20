package com.dflex.ircs.portal.setup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dflex.ircs.portal.setup.entity.RevenueSourceEstimate;

public interface RevenueSourceEstimateRepository extends JpaRepository<RevenueSourceEstimate, Long> {

	@Query("from RevenueSourceEstimate e where e.revenueSource.serviceDepartment.serviceInstitution.id =:serviceInstitutionId "
			+ " and e.financialYearId =:financialYearId and e.recordStatusId =:recordStatusId ")
	public List<RevenueSourceEstimate> findByServiceInstitutionIdAndFinacialYearIdAndRecordStatusId(Long serviceInstitutionId,
			Long financialYearId,Long recordStatusId);

	@Query("from RevenueSourceEstimate e where e.revenueSource.id =:revenueSourceId "
			+ " and e.financialYearId =:financialYearId and e.recordStatusId =:recordStatusId ")
	public RevenueSourceEstimate findByRevenueSourceIdAndFinancialYearIdAndRecordStatusId(Long revenueSourceId,
			Long financialYearId, Long recordStatusId);

}
