package com.dflex.ircs.portal.setup.service;

import java.util.List;
import java.util.Optional;

import com.dflex.ircs.portal.setup.entity.PaymentFacilitator;


/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface PaymentFacilitatorService {
	
	public Optional<PaymentFacilitator> findById(Long id);
	
	public PaymentFacilitator savePaymentFacilitator(PaymentFacilitator paymentFacilitator);
	
	public PaymentFacilitator findByPaymentFacilitatorCode(String paymentFacilitatorCode);
	
	public PaymentFacilitator findByPaymentFacilitatorCodeAndRecordStatusId(String paymentFacilitatorCode,Long statusId);
	
	public List<PaymentFacilitator> findAll();
	
}