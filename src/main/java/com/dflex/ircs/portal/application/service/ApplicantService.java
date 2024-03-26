package com.dflex.ircs.portal.application.service;

import java.util.List;
import java.util.Optional;

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
	
}