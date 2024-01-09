package com.dflex.ircs.portal.setup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dflex.ircs.portal.setup.entity.OtherServiceInstitution;


public interface OtherServiceInstitutionRepository extends JpaRepository<OtherServiceInstitution, Long> {

	public OtherServiceInstitution findByInstitutionCodeAndRecordStatusId(String serviceInstitutionCode, Long statusId);

	public OtherServiceInstitution findByInstitutionCode(String institutionCode);

}
