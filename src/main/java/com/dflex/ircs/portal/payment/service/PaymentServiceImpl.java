package com.dflex.ircs.portal.payment.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.payment.entity.Payment;
import com.dflex.ircs.portal.payment.repository.PaymentRepository;

/**
 * 
 * @author Augustino Mwageni
 *
 */

@Service
public class PaymentServiceImpl implements PaymentService{
	
	@Autowired
	private PaymentRepository paymentRepository;

	@Override
	public Optional<Payment> findById(Long id) {
		return paymentRepository.findById(id);
	}

	@Override
	public Payment savePayment(Payment payment) {
		return paymentRepository.save(payment);
	}

	@Override
	public Payment findByTransactionNumberAndPaymentFacilitatorCode(String transactionNumber,
			String paymentFacilitatorCode) {
		return paymentRepository.findByTransactionNumberAndPaymentFacilitatorCode(transactionNumber,
				paymentFacilitatorCode);
	}

	@Override
	public List<Payment> findByInvoicePaymentNumberAndRecordStatusId(String invoicePaymentNumber, Long recordStatusId) {
		return paymentRepository.findByInvoicePaymentNumberAndRecordStatusId(invoicePaymentNumber,recordStatusId);
	}

	
}