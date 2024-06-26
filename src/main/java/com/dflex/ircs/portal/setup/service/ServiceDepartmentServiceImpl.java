package com.dflex.ircs.portal.setup.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.setup.entity.ServiceDepartment;
import com.dflex.ircs.portal.setup.repository.ServiceDepartmentRepository;


/**
 * 
 * @author Augustino Mwageni
 *
 */

@Service
public class ServiceDepartmentServiceImpl implements ServiceDepartmentService{
	
	@Autowired
	private ServiceDepartmentRepository serviceDepartmentRepository;

	@Override
	public Optional<ServiceDepartment> findById(Long id) {
		return serviceDepartmentRepository.findById(id);
	}

	@Override
	public ServiceDepartment saveServiceDepartment(ServiceDepartment serviceDepartment) {
		return serviceDepartmentRepository.save(serviceDepartment);
	}

	@Override
	public ServiceDepartment findByDepartmentCodeAndServiceInstitutionIdAndRecordStatusId(String serviceDepartmentCode,
			Long serviceInstitutionId, Long recordStatusId) {
		return serviceDepartmentRepository.findByDepartmentCodeAndServiceInstitutionIdAndRecordStatusId(serviceDepartmentCode,
				serviceInstitutionId,recordStatusId);
	}

	@Override
	public List<ServiceDepartment> findAll(){
		return serviceDepartmentRepository.findAll();
	}
}