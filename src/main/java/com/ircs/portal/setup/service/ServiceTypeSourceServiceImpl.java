package com.ircs.portal.setup.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ircs.portal.setup.entity.ServiceTypeSource;
import com.ircs.portal.setup.repository.ServiceTypeSourceRepository;

/**
 * 
 * @author Augustino Mwageni
 *
 */

@Service
public class ServiceTypeSourceServiceImpl implements ServiceTypeSourceService {
	
	@Autowired
	private ServiceTypeSourceRepository serviceTypeSourceRepository;

	@Override
	public Optional<ServiceTypeSource> findById(Long id) {
		return serviceTypeSourceRepository.findById(id);
	}

	@Override
	public ServiceTypeSource saveServiceTypeSource(ServiceTypeSource serviceTypeSource) {
		return serviceTypeSourceRepository.save(serviceTypeSource);
	}

	@Override
	public ServiceTypeSource findByServiceTypeCodeAndServiceDepartmentIdAndFinancialYearIdAndRecordStatusId(
			String serviceTypeCode, Long serviceDepartmentId, Long financialYearId, Long recordStatusId) {
		return serviceTypeSourceRepository.findByServiceTypeCodeAndServiceDepartmentIdAndFinancialYearIdAndRecordStatusId(
				serviceTypeCode,serviceDepartmentId,financialYearId,recordStatusId);
	}

	@Override
	public List<ServiceTypeSource> findByServiceDepartmentIdAndFinancialYearIdAndRecordStatusId(
			Long serviceDepartmentId, Long financialYearId, Long recordStatusId) {
		return serviceTypeSourceRepository.findByServiceDepartmentIdAndFinancialYearIdAndRecordStatusId(
				serviceDepartmentId,financialYearId,recordStatusId);
	}

	@Override
	public List<Map<String, String>> findServiceTypesByServiceDepartmentIdAndFinancialYearIdAndRecordStatusId(
			Long serviceDepartmentId, Long financialYearId, Long recordStatusId) {
		
		List<Map<String, String>> serviceTypeSources = new ArrayList<>();
		List<ServiceTypeSource> serviceTypeSourceList = findByServiceDepartmentIdAndFinancialYearIdAndRecordStatusId(
				serviceDepartmentId,financialYearId,recordStatusId);
		if(serviceTypeSourceList != null && !serviceTypeSourceList.isEmpty()) {
			
			serviceTypeSourceList.forEach(serviceType -> {
				Map<String, String> serviceTypeSource = new HashMap<>();
				serviceTypeSource.put("serviceTypeCode", serviceType.getServiceType().getServiceTypeCode());
				serviceTypeSource.put("serviceTypeDesc", serviceType.getServiceType().getServiceTypeDescription());
				serviceTypeSource.put("fixedAmount", String.valueOf(serviceType.getFixedAmount()));
				serviceTypeSource.put("isFixedAmount", String.valueOf(serviceType.getIsFixedAmount()));
				serviceTypeSource.put("fixedCurrencyId",serviceType.getCurrency()!=null?String.valueOf(serviceType.getCurrency().getId()):null);
				serviceTypeSources.add(serviceTypeSource);
			});
		}
		return serviceTypeSources;
	}

}