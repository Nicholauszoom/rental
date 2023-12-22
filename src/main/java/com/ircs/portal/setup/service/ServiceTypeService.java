package com.ircs.portal.setup.service;

import java.util.Optional;

import com.ircs.portal.setup.entity.ServiceType;


/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface ServiceTypeService {
	
	public Optional<ServiceType> findById(Long id);
	
	public ServiceType saveServiceType(ServiceType serviceType);
	
	public ServiceType findByServiceTypeCodeAndRecordStatusId(String serviceTypeCode,Long statusId);
	
}