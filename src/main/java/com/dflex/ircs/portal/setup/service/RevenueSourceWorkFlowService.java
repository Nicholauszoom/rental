package com.dflex.ircs.portal.setup.service;

import java.util.Optional;

import com.dflex.ircs.portal.setup.entity.RevenueSourceWorkFlow;

public interface RevenueSourceWorkFlowService {

    public Optional<RevenueSourceWorkFlow> findById(Long id);

	public RevenueSourceWorkFlow saveWorkFlow(RevenueSourceWorkFlow revenueSourceWorkFlow);

}
