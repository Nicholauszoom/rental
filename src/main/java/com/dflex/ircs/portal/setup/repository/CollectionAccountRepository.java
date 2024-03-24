package com.dflex.ircs.portal.setup.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dflex.ircs.portal.setup.entity.CollectionAccount;


public interface CollectionAccountRepository extends JpaRepository<CollectionAccount, Long> {

	@Query("from CollectionAccount c where c.currency.id=:currencyId and c.serviceInstitution.id=:serviceInstitutionId "
			+ " and c.paymentFacilitator.id=:paymentFacilitatorId")
	public List<CollectionAccount> findByCurrencyIdAndServiceInstitutionIdAndPaymentFacilitatorId(Long currencyId,
			Long serviceInstitutionId, Long paymentFacilitatorId);

	public List<CollectionAccount> findByCollectionAccountNumber(String collectionAccountNumber);

	@Query("from CollectionAccount c where c.collectionAccountNumber =:collectionAccount and  c.currency.currencyCode=:currencyCode "
			+ " and c.serviceInstitution.id=:serviceInstitutionId and c.paymentFacilitator.id=:paymentFacilitatorId and c.recordStatusId=:recordStatusId")
	public CollectionAccount findByCollectionAccountAndCurrencyCodeAndServiceInstitutionIdAndPaymentFacilitatorIdAndRecordStatusId(
			String collectionAccount, String currencyCode, Long serviceInstitutionId, Long paymentFacilitatorId,Long recordStatusId);

	@Query("from CollectionAccount c where c.collectionAccountNumber =:collectionAccount and  c.currency.currencyCode=:currencyCode "
			+ " and c.serviceInstitution.institutionCode=:serviceInstitutionCode and c.paymentFacilitator.paymentFacilitatorCode=:paymentFacilitatorCode ")
	public CollectionAccount findByCollectionAccountAndCurrencyCodeAndServiceInstitutionCodeAndPaymentFacilitatorCode(
			String collectionAccount, String currencyCode, String serviceInstitutionCode,
			String paymentFacilitatorCode);
	
}
