package com.dflex.ircs.portal.application.repository;

import java.util.List;

import com.dflex.ircs.portal.application.dto.ApplicantDto;

public interface ApplicantRepositoryCustom {
	
	public List<ApplicantDto> findBySeachCriteriaAndRecordStatusId(String applicantAccount,String applicantName,
			String telephoneNumber,String identityNumber,String emailAddress,Long recordStatusId,Long limit);

}
