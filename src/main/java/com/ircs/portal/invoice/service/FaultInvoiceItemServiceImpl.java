package com.ircs.portal.invoice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ircs.portal.invoice.entity.FaultInvoiceItem;
import com.ircs.portal.invoice.repository.FaultInvoiceItemRepository;

/**
 * 
 * @author Augustino Mwageni
 *
 */
@Service
public class FaultInvoiceItemServiceImpl implements FaultInvoiceItemService {
	
	@Autowired
	private FaultInvoiceItemRepository faultInvoiceItemRepository;

	@Override
	public Optional<FaultInvoiceItem> findById(Long id) {
		return faultInvoiceItemRepository.findById(id);
	}

	@Override
	public FaultInvoiceItem saveFaultInvoiceItem(FaultInvoiceItem faultInvoiceItem) {
		return faultInvoiceItemRepository.save(faultInvoiceItem);
	}

	@Override
	public List<FaultInvoiceItem> saveFaultInvoiceItemList(List<FaultInvoiceItem> faultInvoiceItemList) {
		return faultInvoiceItemRepository.saveAll(faultInvoiceItemList);
	}

	
}