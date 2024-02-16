package com.dflex.ircs.portal.setup.service;

import java.util.List;
import java.util.Optional;

import com.dflex.ircs.portal.auth.dto.ClientDetailsDto;
import com.dflex.ircs.portal.setup.entity.OtherServiceInstitution;


/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface OtherServiceInstitutionService {
	
	public Optional<OtherServiceInstitution> findById(Long id);
	
	public OtherServiceInstitution saveOtherServiceInstitution(OtherServiceInstitution otherServiceInstitution);
	
	public OtherServiceInstitution findByInstitutionCodeAndRecordStatusId(String otherServiceInstitutionCode,Long statusId);
	
	public List<OtherServiceInstitution> findAll();

	public ClientDetailsDto findByClientCodeAndRecordStatusId(String clientCode, Long statusId);
	
}