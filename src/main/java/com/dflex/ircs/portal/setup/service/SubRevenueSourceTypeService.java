package com.dflex.ircs.portal.setup.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dflex.ircs.portal.setup.entity.SubRevenueSourceType;

/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface SubRevenueSourceTypeService {
	
	public Optional<SubRevenueSourceType> findById(Long id);
	
	public SubRevenueSourceType saveSubRevenueSourceType(SubRevenueSourceType subRevenueSourceType);
	
	public List<SubRevenueSourceType> findByRecordStatusId(Long recordStatusId);
	
	public Map<Long,Object> findMinListByRecordStatusId(Long recordStatusId);

}