package com.dflex.ircs.portal.invoice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.invoice.entity.InvoiceItem;
import com.dflex.ircs.portal.invoice.repository.InvoiceItemRepository;

/**
 * 
 * @author Augustino Mwageni
 *
 */
@Service
public class InvoiceItemServiceImpl implements InvoiceItemService {
	
	@Autowired
	private InvoiceItemRepository invoiceItemRepository;
	
	public Optional<InvoiceItem> findById(Long id){
		return invoiceItemRepository.findById(id);
	}

	@Override
	public void deleteById(Long id) {
		invoiceItemRepository.deleteById(id);
	}

	@Override
	public InvoiceItem saveInvoiceItem(InvoiceItem invoiceItem) {
		return invoiceItemRepository.save(invoiceItem);
	}

	@Override
	public List<InvoiceItem> saveInvoiceItemList(List<InvoiceItem> invoiceItemList) {
		return invoiceItemRepository.saveAll(invoiceItemList);
	}

	@Override
	public List<InvoiceItem> findByInvoiceIdAndRecordStatusId(Long invoiceId, Long recordStatusId) {
		return invoiceItemRepository.findByInvoiceIdAndRecordStatusId(invoiceId,recordStatusId);
	}

}