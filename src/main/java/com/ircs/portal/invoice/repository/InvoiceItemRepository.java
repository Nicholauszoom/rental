package com.ircs.portal.invoice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ircs.portal.invoice.entity.InvoiceItem;

public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {

}
