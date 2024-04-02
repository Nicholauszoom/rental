package com.dflex.ircs.portal.application.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.dflex.ircs.portal.application.dto.ApplicantDto;
import com.dflex.ircs.portal.application.entity.Applicant;

/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface ApplicantService {
	
	public Optional<Applicant> findById(Long id);
	
	public Applicant saveApplicant(Applicant applicant);
	
	public List<Applicant> findByRecordStatusId(Long recordStatusId);
	
	public Applicant findByIdentityNumberAndIdentityTypeIdAndRecordStatusId(String identityNumber,Long IdentityTypeId,Long recordStatusId);

	public List<ApplicantDto> findBySeachCriteriaAndRecordStatusId(String applicantAccount, String applicantName,
			String telephoneNumber, String identityNumber, String emailAddress, Long recordStatusActive,Long limit);

	public Applicant findByApplicantUid(UUID applicantUid);
	
}