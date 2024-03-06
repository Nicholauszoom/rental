package com.dflex.ircs.portal.data.service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.dflex.ircs.portal.data.entity.FormData1;
import com.dflex.ircs.portal.data.entity.FormData2;
import com.dflex.ircs.portal.module.entity.AppFormField;

public interface FormDataService {

    public Optional<FormData1> findFormData1ById(Long id);
    
    public Optional<FormData2> findFormData2ById(Long id);

	public FormData1 saveFormData1(FormData1 formData);
	
	public FormData2 saveFormData2(FormData2 formData);

    public List<FormData1> findFormData1ByAppFormUidAndRecordStatusId(UUID appFormformUid,Long recordStatusId);

	public List<LinkedHashMap<String,Object>> findAppFormDataListByAppFormUidAndDataPathAndDataFields(UUID appFormformUid,String dataPath,
			List<String> dataListFields);

	public LinkedHashMap<String, Object> findAppFormDataDetailByAppFormUidAndApplicationUidAndDataPathAndDataFields(
			UUID fromString, UUID fromString2, String dataPath, List<AppFormField> dataDetailFields);

}
