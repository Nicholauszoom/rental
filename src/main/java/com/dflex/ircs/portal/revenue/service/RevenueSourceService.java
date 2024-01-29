package com.dflex.ircs.portal.revenue.service;

import com.dflex.ircs.portal.invoice.entity.Invoice;
import com.dflex.ircs.portal.revenue.entity.ResourceRevenue;

import java.util.Optional;

public interface RevenueSourceService {

    public Optional<ResourceRevenue> findById(Long id);
}
