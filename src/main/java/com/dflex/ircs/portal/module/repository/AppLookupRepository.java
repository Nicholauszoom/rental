package com.dflex.ircs.portal.module.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dflex.ircs.portal.module.entity.AppLookup;

/**
 * 
 * @author Augustino Mwageni
 */

public interface AppLookupRepository extends JpaRepository<AppLookup,Long> {

	public List<AppLookup> findByAppLookupTypeIdAndRecordStatusIdOrderByLookupKeyAsc(Long appLookupTypeId, Long recordStatusId);

}