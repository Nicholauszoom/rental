package com.dflex.ircs.portal.setup.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.setup.entity.WorkStation;
import com.dflex.ircs.portal.setup.repository.WorkStationRepository;


/**
 * 
 * @author Augustino Mwageni
 *
 */

@Service
public class WorkStationServiceImpl implements WorkStationService{
	
	@Autowired
	private WorkStationRepository workStationRepository;

	@Override
	public Optional<WorkStation> findById(Long id) {
		return workStationRepository.findById(id);
	}

	@Override
	public WorkStation saveWorkStation(WorkStation workStation) {
		return workStationRepository.save(workStation);
	}

	@Override
	public WorkStation findByWorkStationCode(String workStationCode) {
		return workStationRepository.findByWorkStationCode(workStationCode);
	}

	@Override
	public WorkStation findByWorkStationCodeAndServiceInstitutionIdAndRecordStatusId(String workStationCode,
			Long serviceInstitutionId, Long recordStatusId) {
		return workStationRepository.findByWorkStationCodeAndServiceInstitutionIdAndRecordStatusId(workStationCode,
				serviceInstitutionId,recordStatusId);
	}
}