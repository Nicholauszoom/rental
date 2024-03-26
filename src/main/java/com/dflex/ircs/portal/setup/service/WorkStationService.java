package com.dflex.ircs.portal.setup.service;

import java.util.Optional;

import com.dflex.ircs.portal.setup.entity.WorkStation;


/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface WorkStationService {
	
	public Optional<WorkStation> findById(Long id);
	
	public WorkStation saveWorkStation(WorkStation workStation);
	
	public WorkStation findByWorkStationCode(String workStationCode);

	public WorkStation findByWorkStationCodeAndServiceInstitutionIdAndRecordStatusId(String workStationCode, Long serviceInstitutionId,
			Long recordStatusId);
	
}