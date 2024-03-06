package com.dflex.ircs.portal.data.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.data.entity.FormDataTableField;
import com.dflex.ircs.portal.data.repository.FormDataTableFieldRepository;



@Service
public class FormDataTableFieldServiceImpl implements FormDataTableFieldService {
	
	@Autowired
	private FormDataTableFieldRepository formDataTableFieldRepository;

	@Override
	public Optional<FormDataTableField> findById(Long id) {
		return formDataTableFieldRepository.findById(id);
	}

	@Override
	public FormDataTableField saveFormDataTableField(FormDataTableField formDataTableField) {
		return formDataTableFieldRepository.save(formDataTableField);
	}

	@Override
	public List<FormDataTableField> findByFormDataTableUidAndRecordStatusId(UUID formDataTableUid,
			Long recordStatusId) {
		return formDataTableFieldRepository.findByFormDataTableUidAndRecordStatusId(formDataTableUid,
				recordStatusId);
	}

}