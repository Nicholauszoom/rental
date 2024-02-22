package com.dflex.ircs.portal.setup.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.dflex.ircs.portal.setup.dto.RevenueSourceDetailsDto;
import com.dflex.ircs.portal.setup.entity.RevenueSource;

public interface RevenueSourceService {

    public Optional<RevenueSource> findById(Long id);

	public RevenueSource saveRevenueSource(RevenueSource revenueSource);

    public List<RevenueSourceDetailsDto> findDetailsByServiceInstitutionUidAndAppModuleUidAndRecordStatusId(UUID serviceInstitutionUid,
    		UUID appModuleUid,Long recordStatusId);

	public List<RevenueSourceDetailsDto> findDetailsByServiceDepartmentIdAndAppModuleIdAndRecordStatusId(
			Long departmentId, Long moduleId, Long recordStatusId);

}
