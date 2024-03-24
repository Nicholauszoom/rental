package com.dflex.ircs.portal.setup.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dflex.ircs.portal.setup.entity.RevenueSource;

public interface RevenueSourceRepository extends JpaRepository<RevenueSource, Long>,RevenueSourceRepositoryCustom {

	@Query("from RevenueSource r where r.serviceDepartment.serviceInstitution =:serviceInstitutionId and r.appModuleId =:appModuleId"
			+ " and r.recordStatusId =:recordStatusId")
	public List<RevenueSource> findByServiceInstitutionIdAndAppModuleIdAndRecordStatusId(Long serviceInstitutionId,
			Long appModuleId, Long recordStatusId);

	@Query("from RevenueSource r where r.revenueSourceUid =:revenueSourceUid ")
	public RevenueSource findByRevenueSourceUid(UUID revenueSourceUid);

}
