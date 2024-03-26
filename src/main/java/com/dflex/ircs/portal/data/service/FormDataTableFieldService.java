package com.dflex.ircs.portal.data.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.dflex.ircs.portal.data.entity.FormDataTableField;

public interface FormDataTableFieldService {

    public Optional<FormDataTableField> findById(Long id);

	public FormDataTableField saveFormDataTableField(FormDataTableField formDataTableField);

    public List<FormDataTableField> findByFormDataTableUidAndRecordStatusId(UUID formDataTableUid,Long recordStatusId);

}
