package com.dflex.ircs.portal.setup.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.setup.entity.SubRevenueSource;
import com.dflex.ircs.portal.setup.repository.SubRevenueSourceRepository;


/**
 * 
 * @author Augustino Mwageni
 *
 */

@Service
public class SubRevenueSourceServiceImpl implements SubRevenueSourceService{
	
	@Autowired
	private SubRevenueSourceRepository subRevenueSourceRepository;

	@Override
	public Optional<SubRevenueSource> findById(Long id) {
		return subRevenueSourceRepository.findById(id);
	}

	@Override
	public SubRevenueSource saveSubRevenueSource(SubRevenueSource subRevenueSource) {
		return subRevenueSourceRepository.save(subRevenueSource);
	}

	@Override
	public List<SubRevenueSource> findByMainRevenueSourceIdAndRecordStatusId(Long mainRevenueSourceId,
			Long recordStatusId) {
		return subRevenueSourceRepository.findByMainRevenueSourceIdAndRecordStatusId(mainRevenueSourceId,
				recordStatusId);
	}

}