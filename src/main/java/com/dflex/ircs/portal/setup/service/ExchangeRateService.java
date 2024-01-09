package com.dflex.ircs.portal.setup.service;

import java.util.List;
import java.util.Optional;

import com.dflex.ircs.portal.setup.entity.ExchangeRate;


/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface ExchangeRateService {
	
	public Optional<ExchangeRate> findById(Long id);
	
	public ExchangeRate saveExchangeRate(ExchangeRate exchangeRate);
	
	public List<ExchangeRate> findByCurrencyCodeAndServiceInstitutionIdAndRecordStatusId(String currencyCode,Long serviceInstitutionId,Long statusId);

	public List<ExchangeRate> findByCurrencyCodeAndServiceInstitutionId(String currencyCode,Long serviceInstitutionId);
	
	public List<ExchangeRate> findByRecordStatusId(Long statusId);

}