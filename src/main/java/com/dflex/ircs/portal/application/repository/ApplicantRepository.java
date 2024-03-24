package com.dflex.ircs.portal.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.dflex.ircs.portal.application.entity.Applicant;


@Transactional
public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

	public List<Applicant> findByRecordStatusId(Long recordStatusId);

	public Applicant findByIdentityNumberAndIdentityTypeIdAndRecordStatusId(String identityNumber, Long identityTypeId,
			Long recordStatusId);

}
