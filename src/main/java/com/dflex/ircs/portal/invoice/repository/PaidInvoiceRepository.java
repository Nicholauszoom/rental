package com.dflex.ircs.portal.invoice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dflex.ircs.portal.invoice.entity.PaidInvoice;


public interface PaidInvoiceRepository extends JpaRepository<PaidInvoice, Long> {

	public List<PaidInvoice> findByInvoicePaymentNumber(String invoicePaymentNumber);

	@Query("from PaidInvoice p where p.invoiceNumber =:invoiceNumber and p.serviceInstitution.id =:serviceInstitutionId")
	public  PaidInvoice findByInvoiceNumberAndServiceInstitutionId(String invoiceNumber, Long serviceInstitutionId);

}
