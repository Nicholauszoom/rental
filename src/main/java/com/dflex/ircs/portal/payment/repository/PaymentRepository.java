package com.dflex.ircs.portal.payment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dflex.ircs.portal.payment.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

	public Payment findByTransactionNumberAndPaymentFacilitatorCode(String transactionNumber,
			String paymentFacilitatorCode);

	public List<Payment> findByInvoicePaymentNumberAndRecordStatusId(String invoicePaymentNumber, Long recordStatusId);
}
