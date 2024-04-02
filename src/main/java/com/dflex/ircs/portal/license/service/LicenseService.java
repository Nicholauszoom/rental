package com.dflex.ircs.portal.license.service;

import java.util.List;
import java.util.Optional;

import com.dflex.ircs.portal.license.dto.LicenseDto;
import com.dflex.ircs.portal.license.entity.License;


/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface LicenseService {
	
	public Optional<License> findById(Long id);
	
	public License saveLicense(License invoice);
	
	public List<License> findByApplicantIdAndRecordStatusId(Long applicantId,Long recordStatusId);
	
	public List<License> findByLicenseStatusIdAndRecordStatusId(Long licenseStatusId,Long recordStatusId);
	
	public List<LicenseDto> findBySeachCriteriaAndRecordStatusId(String licenseNumber, String licenseName,
			Long recordStatusId,String applicantAccount,String applicantName,String identityNumber, Long recordlimit);
}