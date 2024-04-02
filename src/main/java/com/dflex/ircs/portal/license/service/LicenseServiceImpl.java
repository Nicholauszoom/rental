package com.dflex.ircs.portal.license.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.license.dto.LicenseDto;
import com.dflex.ircs.portal.license.entity.License;
import com.dflex.ircs.portal.license.repository.LicenseRepository;

/**
 * 
 * @author Augustino Mwageni
 *
 */
@Service
public class LicenseServiceImpl implements LicenseService {
	
	@Autowired
	private LicenseRepository licenseRepository;

	@Override
	public Optional<License> findById(Long id) {
		return licenseRepository.findById(id);
	}

	@Override
	public License saveLicense(License invoice) {
		return licenseRepository.save(invoice);
	}

	@Override
	public List<License> findByApplicantIdAndRecordStatusId(Long applicantId, Long recordStatusId) {
		return licenseRepository.findByApplicantIdAndRecordStatusId(applicantId,recordStatusId);
	}

	@Override
	public List<License> findByLicenseStatusIdAndRecordStatusId(Long licenseStatusId, Long recordStatusId) {
		return licenseRepository.findByLicenseStatusIdAndRecordStatusId(licenseStatusId,recordStatusId);
	}

	@Override
	public List<LicenseDto> findBySeachCriteriaAndRecordStatusId(String licenseNumber, String licenseName,
			Long recordStatusId,String applicantAccount,String applicantName,String identityNumber,Long recordlimit) {
		return licenseRepository.findBySeachCriteriaAndRecordStatusId(licenseNumber,licenseName,
				recordStatusId,applicantAccount,applicantName,identityNumber,recordlimit);
	}
	
	
}