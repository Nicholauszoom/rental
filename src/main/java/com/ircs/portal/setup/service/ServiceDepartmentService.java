package com.ircs.portal.setup.service;

import java.util.Optional;

import com.ircs.portal.setup.entity.ServiceDepartment;


/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface ServiceDepartmentService {
	
	public Optional<ServiceDepartment> findById(Long id);
	
	public ServiceDepartment saveServiceDepartment(ServiceDepartment serviceDepartment);
	
	public ServiceDepartment findByDepartmentCodeAndServiceInstitutionIdAndRecordStatusId(
			String serviceDepartmentCode, Long serviceInstitutionId, Long recordStatusId);
		
}