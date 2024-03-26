package com.dflex.ircs.portal.payment.service;

import java.util.List;
import java.util.Optional;

import com.dflex.ircs.portal.payment.entity.Payment;


/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface PaymentService {
	
	public Optional<Payment> findById(Long id);
	
	public Payment savePayment(Payment payment);
	
	public Payment findByTransactionNumberAndPaymentFacilitatorCode(String transactionNumber,
			String paymentFacilitatorCode);
	
	public List<Payment> findByInvoicePaymentNumberAndRecordStatusId(String invoicePaymentNumber,
			Long recordStatusId);
	
	public List<Payment> findByApplicationNumberAndRecordStatusId(String applicationNumber,
			Long recordStatusId);

	public List<Payment> findAll();


}