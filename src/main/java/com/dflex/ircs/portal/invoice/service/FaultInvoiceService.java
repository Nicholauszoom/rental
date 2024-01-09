package com.dflex.ircs.portal.invoice.service;

import java.util.List;
import java.util.Optional;

import com.dflex.ircs.portal.invoice.entity.FaultInvoice;


/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface FaultInvoiceService {
	
	public Optional<FaultInvoice> findById(Long id);
	
	public FaultInvoice saveFaultInvoice(FaultInvoice faultInvoice);
	
	public List<FaultInvoice> findByInvoicePaymentNumber(String invoicePaymentNumber);
	
}