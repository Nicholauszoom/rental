package com.dflex.ircs.portal.license.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dflex.ircs.portal.license.entity.License;

public interface LicenseRepository extends JpaRepository<License, Long>,LicenseRepositoryCustom {

	@Query("from License l where l.applicant.id =:applicantId and l.recordStatusId =:recordStatusId")
	public List<License> findByApplicantIdAndRecordStatusId(Long applicantId, Long recordStatusId);

	@Query("from License l where l.licenseStatus.id =:licenseStatusId and l.recordStatusId =:recordStatusId")
	public List<License> findByLicenseStatusIdAndRecordStatusId(Long licenseStatusId, Long recordStatusId);

}
