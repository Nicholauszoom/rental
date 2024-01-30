package com.dflex.ircs.portal.revenue.service;

import com.dflex.ircs.portal.revenue.entity.Estimate;
import com.dflex.ircs.portal.revenue.entity.RevenueResource;
import com.dflex.ircs.portal.revenue.entity.SubRevenueResource;

import java.util.Optional;

public interface RevenueSourceService {

    public Optional<RevenueResource> findById(Long id);

    public Optional<SubRevenueResource> findSubById(Long id);

//    public RevenueResource addRevenueSource(RevenueResource revenue);

    public Optional<Estimate> findEstimateById(Long id);

}
