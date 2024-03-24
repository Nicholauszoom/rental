package com.dflex.ircs.portal.setup.service;

import java.util.List;
import java.util.Optional;

import com.dflex.ircs.portal.setup.entity.CollectionAccount;


/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface CollectionAccountService {
	
	public Optional<CollectionAccount> findById(Long id);
	
	public CollectionAccount saveCollectionAccount(CollectionAccount collectionAccount);
	
	public List<CollectionAccount> findByCurrencyIdAndServiceInstitutionIdAndPaymentFacilitatorId(Long currencyId,Long serviceInstitutionId,Long paymentFacilitatorId);
	
	public List<CollectionAccount> findByCollectionAccountNumber(String collectionAccountNumber);

	public CollectionAccount findByCollectionAccountAndCurrencyCodeAndServiceInstitutionIdAndPaymentFacilitatorIdAndRecordStatusId(
			String collectionAccount, String currencyCode, Long serviceInstitutionId, Long paymentFacilitatorId,Long recordStatusId);

	public CollectionAccount findByCollectionAccountAndCurrencyCodeAndServiceInstitutionCodeAndPaymentFacilitatorCode(
			String collectionAccount, String currencyCode, String serviceInstitutionCode, String paymentFacilitatorCode);

}