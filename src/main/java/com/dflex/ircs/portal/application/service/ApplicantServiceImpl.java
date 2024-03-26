package com.dflex.ircs.portal.application.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.application.entity.Applicant;
import com.dflex.ircs.portal.application.repository.ApplicantRepository;

/**
 * 
 * @author Augustino Mwageni
 *
 */
@Service
public class ApplicantServiceImpl implements ApplicantService {
	
	@Autowired
	private ApplicantRepository applicantRepository;

	@Override
	public Optional<Applicant> findById(Long id) {
		return applicantRepository.findById(id);
	}

	@Override
	public Applicant saveApplicant(Applicant applicant) {
		return applicantRepository.save(applicant);
	}

	@Override
	public List<Applicant> findByRecordStatusId(Long recordStatusId) {
		return applicantRepository.findByRecordStatusId(recordStatusId);
	}

	@Override
	public Applicant findByIdentityNumberAndIdentityTypeIdAndRecordStatusId(String identityNumber, Long IdentityTypeId,
			Long recordStatusId) {
		return applicantRepository.findByIdentityNumberAndIdentityTypeIdAndRecordStatusId(identityNumber,IdentityTypeId,
				recordStatusId);
	}

}