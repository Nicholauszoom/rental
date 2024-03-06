package com.dflex.ircs.portal.data.repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import com.dflex.ircs.portal.module.entity.AppFormField;

public interface FormDataRepositoryCustom {

	public List<LinkedHashMap<String,Object>> findAppFormDataListByAppFormUidAndDataPathAndDataFields(UUID appFormformUid,String dataPath, List<String> dataListFields);

	public LinkedHashMap<String, Object> findAppFormDataDetailByAppFormUidAndApplicationUidAndDataPathAndDataFields(
			UUID appFormformUid, UUID applicationUid, String dataPath, List<AppFormField> dataDetailFields);
}
