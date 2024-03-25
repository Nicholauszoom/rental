package com.dflex.ircs.portal.setup.service;

import java.util.Map;
import java.util.Optional;

import com.dflex.ircs.portal.setup.entity.Currency;

/**
 *
 * @author Augustino Mwageni
 *
 */

public interface CurrencyService {

	public Optional<Currency> findById(Long id);

	public Currency saveCurrency(Currency currency);

	public Currency findByCurrencyCode(String currencyCode);

	public Map<Long,String> findByRecordStatusId(Long recordStatusId);

}