package com.dflex.ircs.portal.setup.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.dflex.ircs.portal.setup.entity.ServiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.setup.entity.Currency;
import com.dflex.ircs.portal.setup.repository.CurrencyRepository;


/**
 * 
 * @author Augustino Mwageni
 *
 */

@Service
public class CurrencyServiceImpl implements CurrencyService{
	
	@Autowired
	private CurrencyRepository currencyRepository;

	@Override
	public Optional<Currency> findById(Long id) {
		return currencyRepository.findById(id);
	}

	@Override
	public Currency saveCurrency(Currency currency) {
		return currencyRepository.save(currency);
	}

	@Override
	public Currency findByCurrencyCode(String currencyCode) {
		return currencyRepository.findByCurrencyCode(currencyCode);
	}

	@Override
	public Currency findBycurrencyCodeAndRecordStatusId(String currencyCode , Long statusId){
		return currencyRepository.findBycurrencyCodeAndRecordStatusId(currencyCode,statusId);
	}

	@Override
	public Map<Long,String> findByRecordStatusId(Long recordStatusId) {
		
		Map<Long,String> currencies = new HashMap<>();
		List<Currency> currs = currencyRepository.findByRecordStatusId(recordStatusId);
		if(currs != null && !currs.isEmpty()) {
			currs.stream().forEach(currency -> {
				currencies.put(currency.getId(), currency.getCurrencyName()+" ("+currency.getCurrencyCode()+")");
            });
		}
		return currencies;
	}
}