package com.dflex.ircs.portal.setup.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.setup.entity.RevenueSourceWorkFlow;
import com.dflex.ircs.portal.setup.repository.RevenueSourceWorkFlowRepository;



@Service
public class RevenueSourceWorkFlowServiceImpl implements RevenueSourceWorkFlowService {
	
	@Autowired
	private RevenueSourceWorkFlowRepository revenueSourceWorkFlowRepository;

	@Override
	public Optional<RevenueSourceWorkFlow> findById(Long id) {
		return revenueSourceWorkFlowRepository.findById(id);
	}

	@Override
	public RevenueSourceWorkFlow saveWorkFlow(RevenueSourceWorkFlow revenueSourceWorkFlow) {
		return revenueSourceWorkFlowRepository.save(revenueSourceWorkFlow);
	}

}