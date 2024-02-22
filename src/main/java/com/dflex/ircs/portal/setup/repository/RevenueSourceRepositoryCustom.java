package com.dflex.ircs.portal.setup.repository;

import java.util.List;
import java.util.UUID;

import com.dflex.ircs.portal.setup.dto.RevenueSourceDetailsDto;

public interface RevenueSourceRepositoryCustom {
	
	public List<RevenueSourceDetailsDto> findDetailsByServiceInstitutionUidAndAppModuleUidAndRecordStatusId(
			UUID serviceInstitutionUid, UUID appModuleUid, Long recordStatusId);

	public List<RevenueSourceDetailsDto> findDetailsByServiceDepartmentUidAndAppModuleUidAndRecordStatusId(
			UUID departmentUid, UUID moduleUid, Long recordStatusId);

}
