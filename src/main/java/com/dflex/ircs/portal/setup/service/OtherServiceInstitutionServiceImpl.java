package com.dflex.ircs.portal.setup.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.auth.dto.ClientDetailsDto;
import com.dflex.ircs.portal.setup.entity.OtherServiceInstitution;
import com.dflex.ircs.portal.setup.repository.OtherServiceInstitutionRepository;
import com.dflex.ircs.portal.util.Constants;


/**
 * 
 * @author Augustino Mwageni
 *
 */

@Service
public class OtherServiceInstitutionServiceImpl implements OtherServiceInstitutionService{
	
	@Autowired
	private OtherServiceInstitutionRepository otherServiceInstitutionRepository;

	@Override
	public Optional<OtherServiceInstitution> findById(Long id) {
		return otherServiceInstitutionRepository.findById(id);
	}

	@Override
	public OtherServiceInstitution saveOtherServiceInstitution(OtherServiceInstitution otherServiceInstitution) {
		return otherServiceInstitutionRepository.save(otherServiceInstitution);
	}

	@Override
	public OtherServiceInstitution findByInstitutionCodeAndRecordStatusId(String otherServiceInstitutionCode,Long statusId) {
		return otherServiceInstitutionRepository.findByInstitutionCodeAndRecordStatusId(otherServiceInstitutionCode,statusId);
	}

	@Override
	public List<OtherServiceInstitution> findAll() {
		return otherServiceInstitutionRepository.findAll();
	}

	@Override
	public ClientDetailsDto findByClientCodeAndRecordStatusId(String clientCode, Long statusId) {
		OtherServiceInstitution osi = findByInstitutionCodeAndRecordStatusId(clientCode,statusId);
		if(osi != null) {
			
			return new ClientDetailsDto(osi.getId(),osi.getInstitutionCode(),osi.getInstitutionName(),Constants.CLIENT_CATEGORY_OTHER_SERVICE_INSTITUTION
					,osi.getRecordStatusId());
		}
		return null;
	}
}