package com.dflex.ircs.portal.setup.service;

import java.util.Optional;

import com.dflex.ircs.portal.setup.entity.WorkFlow;

public interface WorkFlowService {

    public Optional<WorkFlow> findById(Long id);

	public WorkFlow saveWorkFlow(WorkFlow workFlow);

}
