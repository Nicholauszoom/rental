package com.dflex.ircs.portal.invoice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dflex.ircs.portal.invoice.entity.PaidInvoiceItem;

public interface PaidInvoiceItemRepository extends JpaRepository<PaidInvoiceItem, Long> {


}
