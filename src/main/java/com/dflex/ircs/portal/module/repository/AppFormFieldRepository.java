package com.dflex.ircs.portal.module.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dflex.ircs.portal.module.entity.AppFormField;

/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface AppFormFieldRepository extends JpaRepository<AppFormField,Long> {

	public List<AppFormField> findByAppFormIdAndRecordStatusId(Long appFormId,Long recordStatusId);

}