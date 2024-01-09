package com.dflex.ircs.portal.setup.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dflex.ircs.portal.setup.entity.ServiceTypeSource;


/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface ServiceTypeSourceService {
	
	public Optional<ServiceTypeSource> findById(Long id);
	
	public ServiceTypeSource saveServiceTypeSource(ServiceTypeSource serviceTypeSource);
	
	public ServiceTypeSource findByServiceTypeCodeAndServiceDepartmentIdAndFinancialYearIdAndRecordStatusId(
			String serviceTypeCode,Long serviceDepartmentId,Long financialYearId, Long recordStatusId);

	public List<ServiceTypeSource> findByServiceDepartmentIdAndFinancialYearIdAndRecordStatusId(
			Long serviceDepartmentId, Long financialYearId, Long recordStatusId);
	
	public List<Map<String,String>> findServiceTypesByServiceDepartmentIdAndFinancialYearIdAndRecordStatusId(
			Long serviceDepartmentId, Long financialYearId, Long recordStatusId);
	
}