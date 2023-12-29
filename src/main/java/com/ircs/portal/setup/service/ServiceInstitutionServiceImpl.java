package com.ircs.portal.setup.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ircs.portal.setup.entity.ServiceInstitution;
import com.ircs.portal.setup.repository.ServiceInstitutionRepository;


/**
 * 
 * @author Augustino Mwageni
 *
 */

@Service
public class ServiceInstitutionServiceImpl implements ServiceInstitutionService{
	
	@Autowired
	private ServiceInstitutionRepository serviceInstitutionRepository;

	@Override
	public Optional<ServiceInstitution> findById(Long id) {
		return serviceInstitutionRepository.findById(id);
	}

	@Override
	public ServiceInstitution saveServiceInstitution(ServiceInstitution serviceInstitution) {
		return serviceInstitutionRepository.save(serviceInstitution);
	}

	@Override
	public ServiceInstitution findByInstitutionCodeAndRecordStatusId(String serviceInstitutionCode,Long statusId) {
		return serviceInstitutionRepository.findByInstitutionCodeAndRecordStatusId(serviceInstitutionCode,statusId);
	}

	@Override
	public List<ServiceInstitution> findAll() {
		return serviceInstitutionRepository.findAll();
	}

	@Override
	public ServiceInstitution findByInstitutionCode(String serviceInstitutionCode) {
		return serviceInstitutionRepository.findByInstitutionCode(serviceInstitutionCode);
	}

}