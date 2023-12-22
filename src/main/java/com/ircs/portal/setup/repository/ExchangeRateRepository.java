package com.ircs.portal.setup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ircs.portal.setup.entity.ExchangeRate;



public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
	
	@Query("from ExchangeRate ex where ex.currency.currencyCode =:currencyCode and ex.serviceInstitutionId=:serviceInstitutionId and ex.recordStatusId=:statusId ")
	public List<ExchangeRate> findByCurrencyCodeAndServiceInstitutionIdAndRecordStatusId(String currencyCode,Long serviceInstitutionId,Long statusId);

	@Query("from ExchangeRate ex where ex.currency.currencyCode =:currencyCode and ex.serviceInstitutionId=:serviceInstitutionId ")
	public List<ExchangeRate> findByCurrencyCodeAndServiceInstitutionId(String currencyCode,Long serviceInstitutionId);
	
	public List<ExchangeRate> findByRecordStatusId(Long statusId);

}
