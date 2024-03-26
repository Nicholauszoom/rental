package com.dflex.ircs.portal.setup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dflex.ircs.portal.setup.entity.Currency;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(path = "currencies", collectionResourceRel = "currencies")
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

	Currency findByCurrencyCode(String currencyCode);

	List<Currency> findByRecordStatusId(Long recordStatusId);

	List<Currency> findCurrencyByCurrencyCode(String currencyCode);

	public Currency findBycurrencyCodeAndRecordStatusId(String currencyCode, Long statusId);



}
