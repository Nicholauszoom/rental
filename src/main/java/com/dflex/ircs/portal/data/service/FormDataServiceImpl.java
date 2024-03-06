package com.dflex.ircs.portal.data.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.data.entity.FormData1;
import com.dflex.ircs.portal.data.entity.FormData2;
import com.dflex.ircs.portal.data.repository.FormData1Repository;
import com.dflex.ircs.portal.data.repository.FormData2Repository;
import com.dflex.ircs.portal.data.repository.FormDataRepositoryCustom;
import com.dflex.ircs.portal.module.entity.AppFormField;



@Service
public class FormDataServiceImpl implements FormDataService {
	
	@Autowired
	private FormData1Repository formData1Repository;
	
	@Autowired
	private FormData2Repository formData2Repository;
	
	@Autowired
	private FormDataRepositoryCustom formDataRepositoryCustom;

	@Override
	public Optional<FormData1> findFormData1ById(Long id) {
		return formData1Repository.findById(id);
	}

	@Override
	public Optional<FormData2> findFormData2ById(Long id) {
		return formData2Repository.findById(id);
	}

	@Override
	public FormData1 saveFormData1(FormData1 formData) {
		return formData1Repository.save(formData);
	}

	@Override
	public FormData2 saveFormData2(FormData2 formData) {
		return formData2Repository.save(formData);
	}

	@Override
	public List<FormData1> findFormData1ByAppFormUidAndRecordStatusId(UUID appFormformUid, Long recordStatusId) {
		return formData1Repository.findByAppFormUidAndRecordStatusId(appFormformUid,recordStatusId);
	}

	@Override
	public List<LinkedHashMap<String,Object>> findAppFormDataListByAppFormUidAndDataPathAndDataFields(UUID appFormformUid,String dataPath, List<String> dataListFields) {
		return formDataRepositoryCustom.findAppFormDataListByAppFormUidAndDataPathAndDataFields(appFormformUid,dataPath,dataListFields);
	}

	@Override
	public LinkedHashMap<String, Object> findAppFormDataDetailByAppFormUidAndApplicationUidAndDataPathAndDataFields(
			UUID appFormformUid, UUID applicationUid, String dataPath, List<AppFormField> dataDetailFields) {
		return formDataRepositoryCustom.findAppFormDataDetailByAppFormUidAndApplicationUidAndDataPathAndDataFields(
				appFormformUid,applicationUid,dataPath,dataDetailFields);
	}

}