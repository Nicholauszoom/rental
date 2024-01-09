package com.dflex.ircs.portal.setup.service;

import java.util.List;
import java.util.Optional;

import com.dflex.ircs.portal.setup.dto.ClientDetailsDto;
import com.dflex.ircs.portal.setup.entity.ServiceInstitution;


/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface ServiceInstitutionService {
	
	public Optional<ServiceInstitution> findById(Long id);
	
	public ServiceInstitution saveServiceInstitution(ServiceInstitution serviceInstitution);
	
	public ServiceInstitution findByInstitutionCodeAndRecordStatusId(String serviceInstitutionCode,Long statusId);
	
	public List<ServiceInstitution> findAll();

	public ServiceInstitution findByInstitutionCode(String serviceInstitutionCode);

	public ClientDetailsDto findByClientCodeAndRecordStatusId(String clientCode, Long statusId);
	
}