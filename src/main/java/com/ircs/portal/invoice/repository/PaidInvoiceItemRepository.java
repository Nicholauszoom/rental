package com.ircs.portal.invoice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ircs.portal.invoice.entity.PaidInvoiceItem;

public interface PaidInvoiceItemRepository extends JpaRepository<PaidInvoiceItem, Long> {


}
