package com.dflex.ircs.portal.setup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dflex.ircs.portal.setup.entity.WorkStation;


public interface WorkStationRepository extends JpaRepository<WorkStation, Long> {

	public WorkStation findByWorkStationCode(String workStationCode);

	public WorkStation findByWorkStationCodeAndServiceInstitutionIdAndRecordStatusId(String workStationCode,
			Long serviceInstitutionId, Long recordStatusId);

}
