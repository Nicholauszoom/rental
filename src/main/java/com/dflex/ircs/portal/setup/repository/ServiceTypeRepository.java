package com.dflex.ircs.portal.setup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dflex.ircs.portal.setup.entity.ServiceType;


public interface ServiceTypeRepository extends JpaRepository<ServiceType, Long> {

	public ServiceType findByServiceTypeCodeAndRecordStatusId(String serviceTypeCode,Long statusId);

}
