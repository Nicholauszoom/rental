package com.dflex.ircs.portal.setup.repository;

import java.util.List;

import com.dflex.ircs.portal.setup.entity.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

import com.dflex.ircs.portal.setup.entity.Currency;


public interface CurrencyRepository extends JpaRepository<Currency, Long> {

	public Currency findByCurrencyCode(String currencyCode);

	public List<Currency> findByRecordStatusId(Long recordStatusId);

	public Currency findBycurrencyCodeAndRecordStatusId(String currencyCode, Long statusId);



}
