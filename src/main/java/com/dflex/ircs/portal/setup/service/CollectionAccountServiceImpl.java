package com.dflex.ircs.portal.setup.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.setup.entity.CollectionAccount;
import com.dflex.ircs.portal.setup.repository.CollectionAccountRepository;

/**
 * 
 * @author Augustino Mwageni
 *
 */

@Service
public class CollectionAccountServiceImpl implements CollectionAccountService{
	
	@Autowired
	private CollectionAccountRepository collectionAccountRepository;

	@Override
	public Optional<CollectionAccount> findById(Long id) {
		return collectionAccountRepository.findById(id);
	}

	@Override
	public CollectionAccount saveCollectionAccount(CollectionAccount collectionAccount) {
		return collectionAccountRepository.save(collectionAccount);
	}

	@Override
	public List<CollectionAccount> findByCurrencyIdAndServiceInstitutionIdAndPaymentFacilitatorId(Long currencyId,
			Long serviceInstitutionId, Long paymentFacilitatorId) {
		return collectionAccountRepository. findByCurrencyIdAndServiceInstitutionIdAndPaymentFacilitatorId(currencyId,
				serviceInstitutionId,paymentFacilitatorId);
	}

	@Override
	public List<CollectionAccount> findByCollectionAccountNumber(String collectionAccountNumber) {
		return collectionAccountRepository.findByCollectionAccountNumber(collectionAccountNumber);
	}

	@Override
	public CollectionAccount findByCollectionAccountAndCurrencyCodeAndServiceInstitutionIdAndPaymentFacilitatorIdAndRecordStatusId(
			String collectionAccount, String currencyCode, Long serviceInstitutionId, Long paymentFacilitatorId,Long recordStatusId) {
		return collectionAccountRepository.findByCollectionAccountAndCurrencyCodeAndServiceInstitutionIdAndPaymentFacilitatorIdAndRecordStatusId(
				collectionAccount,currencyCode,serviceInstitutionId,paymentFacilitatorId,recordStatusId);
	}

	@Override
	public CollectionAccount findByCollectionAccountAndCurrencyCodeAndServiceInstitutionCodeAndPaymentFacilitatorCode(
			String collectionAccount, String currencyCode, String serviceInstitutionCode,
			String paymentFacilitatorCode) {
		return collectionAccountRepository.findByCollectionAccountAndCurrencyCodeAndServiceInstitutionCodeAndPaymentFacilitatorCode(
				collectionAccount,currencyCode,serviceInstitutionCode,
				paymentFacilitatorCode);
	}
	

}