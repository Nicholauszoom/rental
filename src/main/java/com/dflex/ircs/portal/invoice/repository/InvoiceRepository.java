package com.dflex.ircs.portal.invoice.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dflex.ircs.portal.invoice.entity.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

	public Invoice findByInvoicePaymentNumber(String invoicePaymentNumber);

	public Invoice findByInvoiceNumberAndServiceInstitutionCode(String invoiceNumber, String serviceInstitutionCode);

	public List<Invoice> findByPaymentNumber(String paymentNumber);

	public Invoice findByInvoiceNumber(String invoiceNumber);

	@Query("from Invoice i where i.reference =:reference and i.referencePath =:referencePath")
	public Invoice findByReferenceAndReferencePath(String reference, String referencePath);


	@Query("from Invoice i join ServiceInstitution  s ON i.serviceInstitution.id = s.id JOIN ServiceDepartment d on  s.id  = d.serviceInstitution.id  where d.serviceDepartmentUid =:departmentId order by i.createdDate desc")
	List<Invoice> findInvoicesByDepartmentId(UUID departmentId);

}
