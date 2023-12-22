package com.ircs.portal.invoice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ircs.portal.invoice.entity.FaultInvoice;
import com.ircs.portal.invoice.repository.FaultInvoiceRepository;

/**
 * 
 * @author Augustino Mwageni
 *
 */
@Service
public class FaultInvoiceServiceImpl implements FaultInvoiceService {
	
	@Autowired
	private FaultInvoiceRepository faultInvoiceRepository;
	
	public Optional<FaultInvoice> findById(Long id){
		return faultInvoiceRepository.findById(id);
	}

	@Override
	public FaultInvoice saveFaultInvoice(FaultInvoice faultInvoice) {
		return faultInvoiceRepository.save(faultInvoice);
	}

	@Override
	public List<FaultInvoice> findByInvoicePaymentNumber(String invoicePaymentNumber) {
		return faultInvoiceRepository.findByInvoicePaymentNumber(invoicePaymentNumber);
	}

}