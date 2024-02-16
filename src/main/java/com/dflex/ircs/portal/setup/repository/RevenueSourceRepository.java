package com.dflex.ircs.portal.setup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dflex.ircs.portal.setup.dto.RevenueSourceDetailsDto;
import com.dflex.ircs.portal.setup.entity.RevenueSource;

public interface RevenueSourceRepository extends JpaRepository<RevenueSource, Long>,RevenueSourceRepositoryCustom {

	public List<RevenueSource> findByServiceInstitutionIdAndAppModuleIdAndRecordStatusId(Long serviceInstitutionId,
			Long appModuleId, Long recordStatusId);

	public List<RevenueSourceDetailsDto> findDetailsByServiceInstitutionCodeAndAppModuleIdAndRecordStatusId(
			String institutionCode, Long moduleId, Long recordStatusId);

}
