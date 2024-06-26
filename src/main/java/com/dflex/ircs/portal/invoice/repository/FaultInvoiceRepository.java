package com.dflex.ircs.portal.invoice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dflex.ircs.portal.invoice.entity.FaultInvoice;

public interface FaultInvoiceRepository extends JpaRepository<FaultInvoice, Long> {

	public List<FaultInvoice> findByInvoicePaymentNumber(String invoicePaymentNumber);


}
