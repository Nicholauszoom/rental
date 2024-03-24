package com.dflex.ircs.portal.application.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.dflex.ircs.portal.application.entity.ApplicationWorkFlow;


@Transactional
public interface ApplicationWorkFlowRepository extends JpaRepository<ApplicationWorkFlow, Long> {

	public List<ApplicationWorkFlow> findByApplicationUidAndRecordStatusId(UUID applicationUid,Long recordStatusId);

}
