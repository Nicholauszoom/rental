package com.dflex.ircs.portal.setup.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.setup.entity.ServiceType;
import com.dflex.ircs.portal.setup.repository.ServiceTypeRepository;

import javax.swing.text.html.Option;

/**
 * 
 * @author Augustino Mwageni
 *
 */

@Service
public class ServiceTypeServiceImpl implements ServiceTypeService{
	
	@Autowired
	private ServiceTypeRepository serviceTypeRepository;

	@Override
	public Optional<ServiceType> findById(Long id) {
		return serviceTypeRepository.findById(id);
	}
//	@Override
//	public ServiceType findByServiceTypeAndId(String serviceTypeUid, Long id){
//		return serviceTypeRepository.findByServiceTypeAndId(serviceTypeUid,id);
//	}
	@Override
	public ServiceType saveServiceType(ServiceType serviceType) {
		return serviceTypeRepository.save(serviceType);
	}

	@Override
	public ServiceType findByServiceTypeCodeAndRecordStatusId(String serviceTypeCode,Long statusId) {
		return serviceTypeRepository.findByServiceTypeCodeAndRecordStatusId(serviceTypeCode,statusId);
	}
	@Override
	public List<ServiceType> findAll(){return serviceTypeRepository.findAll();}
}