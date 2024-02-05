package com.dflex.ircs.portal.module.service;

import java.util.List;
import java.util.Optional;

import com.dflex.ircs.portal.module.entity.AppFormField;

/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface AppFormFieldService {
	
	public Optional<AppFormField> findById(Long id);
	
	public AppFormField saveAppFormField(AppFormField appFormField);
	
	public List<AppFormField> findByAppFormIdAndRecordStatusId(Long appFormId,Long recordStatusId);
	
}