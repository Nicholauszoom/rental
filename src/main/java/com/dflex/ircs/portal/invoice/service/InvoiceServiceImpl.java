package com.dflex.ircs.portal.invoice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dflex.ircs.portal.invoice.entity.Invoice;
import com.dflex.ircs.portal.invoice.repository.InvoiceRepository;

/**
 * 
 * @author Augustino Mwageni
 *
 */
@Service
public class InvoiceServiceImpl implements InvoiceService {
	
	@Autowired
	private InvoiceRepository invoiceRepository;
	
	public Optional<Invoice> findById(Long id){
		return invoiceRepository.findById(id);
	}

	@Override
	public Invoice saveInvoice(Invoice invoice) {
		return invoiceRepository.save(invoice);
	}

	@Override
	public Invoice findByInvoicePaymentNumber(String invoicePaymentNumber) {
		return invoiceRepository.findByInvoicePaymentNumber(invoicePaymentNumber);
	}

	@Override
	public Invoice findByInvoiceNumberAndServiceInstitutionCode(String invoiceNumber, String serviceInstitutionCode) {
		return invoiceRepository.findByInvoiceNumberAndServiceInstitutionCode(invoiceNumber,serviceInstitutionCode);
	}

	@Override
	public void deleteById(Long id) {
        invoiceRepository.deleteById(id);
    }

	@Override
	public List<Invoice> findByPaymentNumber(String paymentNumber) {
		return invoiceRepository.findByPaymentNumber(paymentNumber);
	}
	public List<Invoice> findAll(){
		return invoiceRepository.findAll();
	}

	@Override
	public Invoice findByInvoiceNumber(String invoiceNumber) {
		return invoiceRepository.findByInvoiceNumber(invoiceNumber);
	}

	@Override
	public Invoice findByReferenceAndReferencePath(String reference, String referencePath) {
		return invoiceRepository.findByReferenceAndReferencePath(reference, referencePath);
	}
	
	
}