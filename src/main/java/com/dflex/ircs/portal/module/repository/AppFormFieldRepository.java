package com.dflex.ircs.portal.module.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dflex.ircs.portal.module.entity.AppFormField;

/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface AppFormFieldRepository extends JpaRepository<AppFormField,Long> {

	public List<AppFormField> findByAppFormUidAndRecordStatusId(UUID appFormUid,Long recordStatusId);

}