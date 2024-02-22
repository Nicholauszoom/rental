package com.dflex.ircs.portal.module.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dflex.ircs.portal.module.entity.AppModule;

/**
 * 
 * @author Augustino Mwageni
 */

public interface AppModuleRepository extends JpaRepository<AppModule,Long> {

	public List<AppModule> findByRecordStatusId(Long recordStatusId);

}