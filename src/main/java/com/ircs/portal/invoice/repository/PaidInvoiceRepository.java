package com.ircs.portal.invoice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ircs.portal.invoice.entity.PaidInvoice;

public interface PaidInvoiceRepository extends JpaRepository<PaidInvoice, Long> {

	public List<PaidInvoice> findByInvoicePaymentNumber(String invoicePaymentNumber);

}
