package com.dflex.ircs.portal.invoice.service;

import java.util.List;
import java.util.Optional;

import com.dflex.ircs.portal.invoice.entity.Invoice;

/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface InvoiceService {
	
	public Optional<Invoice> findById(Long id);
	
	public Invoice saveInvoice(Invoice invoice);
	
	public Invoice findByInvoicePaymentNumber(String invoicePaymentNumber);
	
	public Invoice findByInvoiceNumberAndServiceInstitutionCode(String invoiceNumber,String serviceInstitutionCode);
	
	public void deleteById(Long id);

	public List<Invoice> findByPaymentNumber(String paymentNumber);

	public List<Invoice> findAll();
	
}