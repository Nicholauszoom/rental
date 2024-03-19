package com.dflex.ircs.portal.setup.service;

import java.util.List;
import java.util.Optional;

import com.dflex.ircs.portal.setup.entity.ServiceDepartment;


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

	public List<ServiceDepartment> findAll();
		
}