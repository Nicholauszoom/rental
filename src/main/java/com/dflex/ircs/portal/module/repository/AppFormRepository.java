package com.dflex.ircs.portal.module.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dflex.ircs.portal.module.entity.AppForm;

/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface AppFormRepository extends JpaRepository<AppForm,Long> {

	@Query("from AppForm a where a.appFormUid =:appFormUid and a.recordStatusId =:recordStatusId")
	public AppForm findByAppFormUidAndRecordStatusId(UUID appFormUid, Long recordStatusId);

}