package com.dflex.ircs.portal.setup.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.setup.entity.ExchangeRate;
import com.dflex.ircs.portal.setup.repository.ExchangeRateRepository;


/**
 * 
 * @author Augustino Mwageni
 *
 */

@Service
public class ExchangeRateServiceImpl implements ExchangeRateService{
	
	@Autowired
	private ExchangeRateRepository exchangeRateRepository;

	@Override
	public Optional<ExchangeRate> findById(Long id) {
		return exchangeRateRepository.findById(id);
	}

	@Override
	public ExchangeRate saveExchangeRate(ExchangeRate exchangeRate) {
		return exchangeRateRepository.save(exchangeRate);
	}

	@Override
	public List<ExchangeRate> findByCurrencyCodeAndServiceInstitutionIdAndRecordStatusId(String currencyCode,
			Long serviceInstitutionId, Long statusId) {
		return exchangeRateRepository.findByCurrencyCodeAndServiceInstitutionIdAndRecordStatusId(currencyCode,
				serviceInstitutionId,statusId);
	}

	@Override
	public List<ExchangeRate> findByCurrencyCodeAndServiceInstitutionId(String currencyCode,
			Long serviceInstitutionId) {
		return exchangeRateRepository.findByCurrencyCodeAndServiceInstitutionId(currencyCode,serviceInstitutionId);
	}

	@Override
	public List<ExchangeRate> findByRecordStatusId(Long statusId) {
		return exchangeRateRepository.findByRecordStatusId(statusId);
	}

}