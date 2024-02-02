package com.dflex.ircs.portal.revenue.service;

import com.dflex.ircs.portal.revenue.entity.Estimate;
import com.dflex.ircs.portal.revenue.entity.RevenueResource;
import com.dflex.ircs.portal.revenue.entity.SubRevenueResource;
import com.dflex.ircs.portal.revenue.repository.EstimateRepository;
import com.dflex.ircs.portal.revenue.repository.ResourceRevenueRepository;
import com.dflex.ircs.portal.revenue.repository.SubRevenueResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class RevenueSourceImps  implements RevenueSourceService{

    @Autowired
    private ResourceRevenueRepository revenueSourceRepository;

    @Autowired
    private SubRevenueResourceRepository subRevenueSourceRepository;

    @Autowired
    private EstimateRepository estimateRepository;

      private Date date = new Date();
    // create the revenueResource
    public RevenueResource addSource(RevenueResource revenueSource) {
        RevenueResource source = new RevenueResource();
        source.setRevenueDesc(revenueSource.getRevenueDesc());
        source.setRevenueCode(revenueSource.getRevenueCode());
        source.setInstitutionCode(revenueSource.getInstitutionCode());
        source.setGlCode(revenueSource.getGlCode());
        source.setUnityCode(revenueSource.getUnityCode());
        source.setCreatedDate(date);
        source.setUpdatedDate(date);
        source.setCreatedBy(revenueSource.getCreatedBy());
        source.setUpdatedBy(revenueSource.getUpdatedBy());
        source.setCreatedByUserName(revenueSource.getCreatedByUserName());
        source.setUpdatedByUserName(revenueSource.getUpdatedByUserName());

        return revenueSourceRepository.save(source);


    }
    @Override
    public Optional<RevenueResource> findById(Long id) {
        return revenueSourceRepository.findById(id);
    }


    public List<RevenueResource> findAll() {
        return  revenueSourceRepository.findAll();
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
