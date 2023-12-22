package com.ircs.portal.invoice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ircs.portal.invoice.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

	public Invoice findByInvoicePaymentNumber(String invoicePaymentNumber);

	public Invoice findByInvoiceNumberAndServiceInstitutionCode(String invoiceNumber, String serviceInstitutionCode);

	public List<Invoice> findByPaymentNumber(String paymentNumber);

}
