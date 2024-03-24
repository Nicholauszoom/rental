package com.dflex.ircs.portal.invoice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.invoice.entity.PaidInvoiceItem;
import com.dflex.ircs.portal.invoice.repository.PaidInvoiceItemRepository;


/**
 * 
 * @author Augustino Mwageni
 *
 */
@Service
public class PaidInvoiceItemServiceImpl implements PaidInvoiceItemService {
	
	@Autowired
	private PaidInvoiceItemRepository paidInvoiceItemRepository;

	@Override
	public Optional<PaidInvoiceItem> findById(Long id) {
		return paidInvoiceItemRepository.findById(id);
	}

	@Override
	public PaidInvoiceItem savePaidInvoiceItem(PaidInvoiceItem paidInvoiceItem) {
		return paidInvoiceItemRepository.save(paidInvoiceItem);
	}

	@Override
	public List<PaidInvoiceItem> savePaidInvoiceItems(List<PaidInvoiceItem> paidInvoiceItems) {
		return paidInvoiceItemRepository.saveAll(paidInvoiceItems);
	}

	@Override
	public List<PaidInvoiceItem> findByPaidInvoiceId(Long paidInvoiceId) {
		return paidInvoiceItemRepository.findByPaidInvoiceId(paidInvoiceId);
	}
	
}