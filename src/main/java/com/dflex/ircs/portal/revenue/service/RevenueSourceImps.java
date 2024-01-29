package com.dflex.ircs.portal.revenue.service;

import com.dflex.ircs.portal.revenue.entity.ResourceRevenue;
import com.dflex.ircs.portal.revenue.repository.ResourceRevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RevenueSourceImps  implements RevenueSourceService{

    @Autowired
    private ResourceRevenueRepository revenueSourceRepository;

    public ResourceRevenue addSource(ResourceRevenue revenueSource) {
        return revenueSourceRepository.save(revenueSource);

    }
    @Override
    public Optional<ResourceRevenue> findById(Long id) {
        return revenueSourceRepository.findById(id);
    }
}
