package com.dflex.ircs.portal.setup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dflex.ircs.portal.setup.entity.SubRevenueSourceType;


public interface SubRevenueSourceTypeRepository extends JpaRepository<SubRevenueSourceType, Long> {

	public List<SubRevenueSourceType> findByRecordStatusId(Long recordStatusId);

}
