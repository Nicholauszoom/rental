package com.dflex.ircs.portal.invoice.service;

import java.util.List;
import java.util.Optional;

import com.dflex.ircs.portal.invoice.entity.FaultInvoiceItem;


/**
 * 
 * @author Augustino Mwageni
 *
 */

public interface FaultInvoiceItemService {
	
	public Optional<FaultInvoiceItem> findById(Long id);
	
	public FaultInvoiceItem saveFaultInvoiceItem(FaultInvoiceItem faultInvoiceItem);
	
	public List<FaultInvoiceItem> saveFaultInvoiceItemList(List<FaultInvoiceItem> faultInvoiceItemList);
	
}