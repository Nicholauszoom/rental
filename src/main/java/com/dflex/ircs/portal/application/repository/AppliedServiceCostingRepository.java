package com.dflex.ircs.portal.application.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.dflex.ircs.portal.application.entity.AppliedServiceCosting;


@Transactional
public interface AppliedServiceCostingRepository extends JpaRepository<AppliedServiceCosting, Long> {

	public List<AppliedServiceCosting> findByReferenceApplicationIdAndReferenceApplicationTableAndRecordStatusId(
			Long referenceApplicationId,String referenceApplicationTable,Long recordStatusId);

}
