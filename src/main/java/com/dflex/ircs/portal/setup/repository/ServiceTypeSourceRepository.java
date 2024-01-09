package com.dflex.ircs.portal.setup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dflex.ircs.portal.setup.entity.ServiceTypeSource;


public interface ServiceTypeSourceRepository extends JpaRepository<ServiceTypeSource, Long> {

	@Query("from ServiceTypeSource s where s.serviceType.serviceTypeCode =:serviceTypeCode and s.serviceDepartment.id =:serviceDepartmentId "
			+ " and s.financialYearId =:financialYearId and s.recordStatusId =:recordStatusId")
	public ServiceTypeSource findByServiceTypeCodeAndServiceDepartmentIdAndFinancialYearIdAndRecordStatusId(
			String serviceTypeCode, Long serviceDepartmentId, Long financialYearId, Long recordStatusId);

	@Query("from ServiceTypeSource s where s.serviceDepartment.id =:serviceDepartmentId "
			+ " and s.financialYearId =:financialYearId and s.recordStatusId =:recordStatusId")
	public List<ServiceTypeSource> findByServiceDepartmentIdAndFinancialYearIdAndRecordStatusId(
			Long serviceDepartmentId, Long financialYearId, Long recordStatusId);

}
