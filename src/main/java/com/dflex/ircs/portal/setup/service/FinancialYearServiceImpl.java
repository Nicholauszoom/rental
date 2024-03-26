package com.dflex.ircs.portal.setup.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.setup.entity.FinancialYear;
import com.dflex.ircs.portal.setup.repository.FinancialYearRepository;



/**
 * 
 * @author Augustino Mwageni
 *
 */

@Service
public class FinancialYearServiceImpl implements FinancialYearService {

	@Autowired
	private FinancialYearRepository  financialYearRepository;

	@Override
	public Optional<FinancialYear> findById(Long id) {
		return financialYearRepository.findById(id);
	}

	@Override
	public Optional<FinancialYear> findByFinancialYearUid(UUID financialYearUid) {
		return financialYearRepository.findByFinancialYearUid(financialYearUid);
	}

	@Override
	public FinancialYear createFinancialYear(FinancialYear financialYear) {
		return financialYearRepository.save(financialYear);
	}
	@Override
	public  FinancialYear findByFinancialYearIdAndRecordStatusId(Long financialYearId ,Long recordStatusId){
		return financialYearRepository.findByFinancialYearIdAndRecordStatusId(financialYearId,recordStatusId);
	}


}
