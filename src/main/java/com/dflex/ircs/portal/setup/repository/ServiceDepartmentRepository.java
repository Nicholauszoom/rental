package com.dflex.ircs.portal.setup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dflex.ircs.portal.setup.entity.ServiceDepartment;


public interface ServiceDepartmentRepository extends JpaRepository<ServiceDepartment, Long> {

	public ServiceDepartment findByDepartmentCodeAndServiceInstitutionIdAndRecordStatusId(String serviceDepartmentCode,
			Long serviceInstitutionId, Long recordStatusId);

}
