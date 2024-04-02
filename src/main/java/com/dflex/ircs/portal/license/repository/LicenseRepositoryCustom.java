package com.dflex.ircs.portal.license.repository;

import java.util.List;

import com.dflex.ircs.portal.license.dto.LicenseDto;

public interface LicenseRepositoryCustom {

	public List<LicenseDto> findBySeachCriteriaAndRecordStatusId(String licenseNumber, String licenseName,
			Long recordStatusId,String applicantAccount,String applicantName,String identityNumber,Long recordlimit);
}
