package com.dflex.ircs.portal.setup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dflex.ircs.portal.setup.entity.SubRevenueSource;


public interface SubRevenueSourceRepository extends JpaRepository<SubRevenueSource, Long> {

	public List<SubRevenueSource> findByMainRevenueSourceIdAndRecordStatusId(Long mainRevenueSourceId, Long recordStatusId);
}
