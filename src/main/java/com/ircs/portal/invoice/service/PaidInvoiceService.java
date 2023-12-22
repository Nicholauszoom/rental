package com.ircs.portal.invoice.service;

import java.util.List;
import java.util.Optional;

import com.ircs.portal.invoice.entity.PaidInvoice;

/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface PaidInvoiceService {
	
	public Optional<PaidInvoice> findById(Long id);
	
	public PaidInvoice savePaidInvoice(PaidInvoice paidInvoice);
	
	public List<PaidInvoice> findByInvoicePaymentNumber(String invoicePaymentNumber);
	
}