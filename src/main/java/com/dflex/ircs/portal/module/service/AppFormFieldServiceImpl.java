package com.dflex.ircs.portal.module.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.module.entity.AppFormField;
import com.dflex.ircs.portal.module.repository.AppFormFieldRepository;

/**
 * 
 * @author Augustino Mwageni
 *
 */
@Service
public class AppFormFieldServiceImpl implements AppFormFieldService {
	
	@Autowired
	private AppFormFieldRepository appFormFieldRepository;

	@Override
	public Optional<AppFormField> findById(Long id) {
		return appFormFieldRepository.findById(id);
	}

	@Override
	public AppFormField saveAppFormField(AppFormField appFormField) {
		return appFormFieldRepository.save(appFormField);
	}

	@Override
	public List<AppFormField> findByAppFormUidAndRecordStatusId(UUID appFormUid, Long recordStatusId){
		return appFormFieldRepository.findByAppFormUidAndRecordStatusId(appFormUid,recordStatusId);
	}

	@Override
	public List<String> findAppFormDataListFieldsByAppFormUid(UUID appFormUid) {
		return appFormFieldRepository.findAppFormDataListFieldsByAppFormUid(appFormUid);
	}

	@Override
	public List<AppFormField> findByAppFormUidAndShowOnDetailAndRecordStatusId(UUID appFormUid, Boolean showOnDetail,
			Long recordStatusId) {
		return appFormFieldRepository.findByAppFormUidAndShowOnDetailAndRecordStatusId(appFormUid,showOnDetail,
				recordStatusId);
	}
	
}