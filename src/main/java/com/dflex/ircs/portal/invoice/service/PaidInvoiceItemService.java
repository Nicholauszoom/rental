package com.dflex.ircs.portal.invoice.service;

import java.util.List;
import java.util.Optional;

import com.dflex.ircs.portal.invoice.entity.PaidInvoiceItem;


/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface PaidInvoiceItemService {
	
	public Optional<PaidInvoiceItem> findById(Long id);
	
	public PaidInvoiceItem savePaidInvoiceItem(PaidInvoiceItem paidInvoiceItem);
	
	public List<PaidInvoiceItem> savePaidInvoiceItems(List<PaidInvoiceItem> paidInvoiceItems);
	
	public List<PaidInvoiceItem> findByPaidInvoiceId(Long paidInvoiceId);
	
}