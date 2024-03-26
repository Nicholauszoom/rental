package com.dflex.ircs.portal.setup.service;

import java.util.Optional;
import java.util.UUID;

import com.dflex.ircs.portal.setup.entity.FinancialYear;


/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface FinancialYearService {

	public Optional<FinancialYear> findById(Long id);
	
	public Optional<FinancialYear> findByFinancialYearUid(UUID financialYearUid);
	
	public FinancialYear createFinancialYear(FinancialYear financialYear);

	public FinancialYear findByFinancialYearIdAndRecordStatusId(Long financialYearId, Long recordStatusId);
}
