package com.ircs.portal.setup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ircs.portal.setup.entity.ServiceInstitution;


public interface ServiceInstitutionRepository extends JpaRepository<ServiceInstitution, Long> {

	public ServiceInstitution findByInstitutionCodeAndRecordStatusId(String serviceInstitutionCode, Long statusId);

	public ServiceInstitution findByInstitutionCode(String serviceInstitutionCode);

}
