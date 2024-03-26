package com.dflex.ircs.portal.setup.service;

import java.util.List;
import java.util.Optional;

import com.dflex.ircs.portal.setup.entity.ServiceType;


/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface ServiceTypeService {
	
	public Optional<ServiceType> findById(Long id);

//	public ServiceType findByServiceTypeAndId(String serviceTypeUid, Long id);
	
	public ServiceType saveServiceType(ServiceType serviceType);
	
	public ServiceType findByServiceTypeCodeAndRecordStatusId(String serviceTypeCode,Long statusId);

	public List<ServiceType> findAll();
}