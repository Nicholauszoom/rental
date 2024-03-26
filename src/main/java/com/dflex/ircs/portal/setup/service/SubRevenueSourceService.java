package com.dflex.ircs.portal.setup.service;

import java.util.List;
import java.util.Optional;

import com.dflex.ircs.portal.setup.entity.SubRevenueSource;

/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface SubRevenueSourceService {
	
	public Optional<SubRevenueSource> findById(Long id);
	
	public SubRevenueSource saveSubRevenueSource(SubRevenueSource subRevenueSource);
	
	public List<SubRevenueSource> findByMainRevenueSourceIdAndRecordStatusId(Long mainRevenueSourceId,Long recordStatusId);
	
}