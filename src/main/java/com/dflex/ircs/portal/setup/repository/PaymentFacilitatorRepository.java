package com.dflex.ircs.portal.setup.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.dflex.ircs.portal.setup.entity.PaymentFacilitator;



public interface PaymentFacilitatorRepository extends JpaRepository<PaymentFacilitator, Long> {
	
	public PaymentFacilitator findByPaymentFacilitatorCode(String paymentFacilitatorCode);

	public PaymentFacilitator findByPaymentFacilitatorCodeAndRecordStatusId(String paymentFacilitatorCode, Long statusId);

}
