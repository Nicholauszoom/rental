package com.dflex.ircs.portal.setup.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dflex.ircs.portal.setup.entity.WorkFlow;

public interface WorkFlowRepository extends JpaRepository<WorkFlow, Long> {

	
}
