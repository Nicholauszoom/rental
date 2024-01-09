package com.dflex.ircs.portal.invoice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dflex.ircs.portal.invoice.entity.InvoiceItem;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {

	public List<InvoiceItem> findByInvoiceIdAndRecordStatusId(Long invoiceId, Long recordStatusId);

}
