package com.dflex.ircs.portal.invoice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dflex.ircs.portal.invoice.entity.FaultInvoiceItem;

public interface FaultInvoiceItemRepository extends JpaRepository<FaultInvoiceItem, Long> {

	public Optional<FaultInvoiceItem> findById(Long faultInvoiceId);

}
