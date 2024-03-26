package com.dflex.ircs.portal.data.service;

import java.util.List;
import java.util.Optional;

import com.dflex.ircs.portal.data.entity.FormDataTable;

public interface FormDataTableService {

    public Optional<FormDataTable> findById(Long id);

	public FormDataTable saveFormDataTable(FormDataTable formDataTable);

    public List<FormDataTable> findByRecordStatusId(Long recordStatusId);

}
