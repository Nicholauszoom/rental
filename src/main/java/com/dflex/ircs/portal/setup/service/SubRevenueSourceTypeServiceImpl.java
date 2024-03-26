package com.dflex.ircs.portal.setup.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.setup.entity.SubRevenueSourceType;
import com.dflex.ircs.portal.setup.repository.SubRevenueSourceTypeRepository;


/**
 * 
 * @author Augustino Mwageni
 *
 */

@Service
public class SubRevenueSourceTypeServiceImpl implements SubRevenueSourceTypeService {
	
	@Autowired
	private SubRevenueSourceTypeRepository subRevenueSourceTypeRepository;

	@Override
	public Optional<SubRevenueSourceType> findById(Long id) {
		return subRevenueSourceTypeRepository.findById(id);
	}

	@Override
	public SubRevenueSourceType saveSubRevenueSourceType(SubRevenueSourceType subRevenueSourceType) {
		return subRevenueSourceTypeRepository.save(subRevenueSourceType);
	}

	@Override
	public List<SubRevenueSourceType> findByRecordStatusId(Long recordStatusId) {
		return subRevenueSourceTypeRepository.findByRecordStatusId(recordStatusId);
	}
	
	@Override
	public Map<Long, Object> findMinListByRecordStatusId(Long recordStatusId) {
		List<SubRevenueSourceType> subTypes = findByRecordStatusId(recordStatusId);
		Map<Long,Object> minTypes = new HashMap<>();
		if(subTypes != null && !subTypes.isEmpty()) {
			subTypes.stream().forEach(sub -> {
				minTypes.put(sub.getId(), sub.getSubRevenueSourceTypeName());
            });
		}
		return minTypes;
	}

}