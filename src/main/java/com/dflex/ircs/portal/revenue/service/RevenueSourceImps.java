package com.dflex.ircs.portal.revenue.service;

import com.dflex.ircs.portal.revenue.entity.Estimate;
import com.dflex.ircs.portal.revenue.entity.RevenueResource;
import com.dflex.ircs.portal.revenue.entity.SubRevenueResource;
import com.dflex.ircs.portal.revenue.repository.EstimateRepository;
import com.dflex.ircs.portal.revenue.repository.ResourceRevenueRepository;
import com.dflex.ircs.portal.revenue.repository.SubRevenueResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RevenueSourceImps  implements RevenueSourceService{

    @Autowired
    private ResourceRevenueRepository revenueSourceRepository;

    @Autowired
    private SubRevenueResourceRepository subRevenueSourceRepository;

    @Autowired
    private EstimateRepository estimateRepository;


    // create the revenueResource
    public RevenueResource addSource(RevenueResource revenueSource) {
        return revenueSourceRepository.save(revenueSource);

    }
    @Override
    public Optional<RevenueResource> findById(Long id) {
        return revenueSourceRepository.findById(id);
    }

    public SubRevenueResource addSubRevenueSource(SubRevenueResource subRevenue) {
        return subRevenueSourceRepository.save(subRevenue);
    }
    @Override
    public Optional<SubRevenueResource> findSubById(Long id) {
        return subRevenueSourceRepository.findById(id);
    }

    public Estimate addEstimate(Estimate estimate) {
        return estimateRepository.save(estimate);

    }
    @Override
    public Optional<Estimate> findEstimateById(Long id) {
        return estimateRepository.findById(id);
    }

}
