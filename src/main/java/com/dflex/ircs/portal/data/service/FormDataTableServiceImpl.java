package com.dflex.ircs.portal.data.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.data.entity.FormDataTable;
import com.dflex.ircs.portal.data.repository.FormDataTableRepository;



@Service
public class FormDataTableServiceImpl implements FormDataTableService {
	
	@Autowired
	private FormDataTableRepository formDataTableRepository;

	@Override
	public Optional<FormDataTable> findById(Long id) {
		return formDataTableRepository.findById(id);
	}

	@Override
	public FormDataTable saveFormDataTable(FormDataTable formDataTable) {
		return formDataTableRepository.save(formDataTable);
	}

	@Override
	public List<FormDataTable> findByRecordStatusId(Long recordStatusId) {
		return formDataTableRepository.findByRecordStatusId(recordStatusId);
	}

}