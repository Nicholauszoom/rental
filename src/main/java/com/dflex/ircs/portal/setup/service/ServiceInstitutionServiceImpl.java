package com.dflex.ircs.portal.setup.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.setup.dto.ClientDetailsDto;
import com.dflex.ircs.portal.setup.entity.ServiceInstitution;
import com.dflex.ircs.portal.setup.repository.ServiceInstitutionRepository;
import com.dflex.ircs.portal.util.Constants;


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

	@Override
	public ClientDetailsDto findByClientCodeAndRecordStatusId(String clientCode, Long statusId) {
		ServiceInstitution si = findByInstitutionCodeAndRecordStatusId(clientCode,statusId);
		if(si != null) {
			
			return new ClientDetailsDto(si.getId(),si.getInstitutionCode(),si.getInstitutionName(),Constants.CLIENT_CATEGORY_SERVICE_INSTITUTION
					,si.getRecordStatusId());
		}
		return null;
	}
}