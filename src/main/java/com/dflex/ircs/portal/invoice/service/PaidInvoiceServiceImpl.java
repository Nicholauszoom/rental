package com.dflex.ircs.portal.invoice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.invoice.entity.PaidInvoice;
import com.dflex.ircs.portal.invoice.repository.PaidInvoiceRepository;

/**
 * 
 * @author Augustino Mwageni
 *
 */
@Service
public class PaidInvoiceServiceImpl implements PaidInvoiceService {
	
	@Autowired
	private PaidInvoiceRepository paidinvoiceRepository;
	
	public Optional<PaidInvoice> findById(Long id){
		return paidinvoiceRepository.findById(id);
	}

	@Override
	public PaidInvoice savePaidInvoice(PaidInvoice paidInvoice) {
		return paidinvoiceRepository.save(paidInvoice);
	}

	@Override
	public List<PaidInvoice> findByInvoicePaymentNumber(String invoicePaymentNumber) {
		return paidinvoiceRepository.findByInvoicePaymentNumber(invoicePaymentNumber);
	}

	
}