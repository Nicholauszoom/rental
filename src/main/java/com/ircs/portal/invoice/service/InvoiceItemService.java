package com.ircs.portal.invoice.service;

import java.util.List;
import java.util.Optional;

import com.ircs.portal.invoice.entity.InvoiceItem;

/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface InvoiceItemService {
	
	public Optional<InvoiceItem> findById(Long id);
	
	public InvoiceItem saveInvoiceItem(InvoiceItem invoiceItem);
	
	public List<InvoiceItem> saveInvoiceItemList(List<InvoiceItem> invoiceItemList);
	
	public void deleteById(Long id);
	
}