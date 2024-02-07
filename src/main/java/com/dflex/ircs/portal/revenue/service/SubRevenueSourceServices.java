package com.dflex.ircs.portal.revenue.service;

import com.dflex.ircs.portal.revenue.api.controller.SubRevenueController;
import com.dflex.ircs.portal.revenue.entity.RevenueResource;
import com.dflex.ircs.portal.revenue.entity.SubRevenueResource;
import com.dflex.ircs.portal.revenue.repository.SubRevenueResourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SubRevenueSourceServices {

    @Autowired
    private SubRevenueResourceRepository subRevenueSourceRepository;

    @Autowired
    private RevenueSourceImps revenueSourceImps;


    private Date date = new Date();
    protected Logger logger = LoggerFactory.getLogger(SubRevenueSourceServices.class);

    public SubRevenueResource addSubRevenueSource(SubRevenueResource subRevenue) {


        Optional<RevenueResource> revenueResourceOptional = revenueSourceImps.findById(subRevenue.getRevenueResource().getId());

          logger.info("RevenueResource with ID {}: " + subRevenue.getRevenueResource().getId() + " found",revenueResourceOptional);
        if (revenueResourceOptional.isPresent()) {
            RevenueResource revenueResource = revenueResourceOptional.get();

            SubRevenueResource subRevenueResource = new SubRevenueResource();
            subRevenueResource.setSubRevenueCode(subRevenue.getSubRevenueCode());
            subRevenueResource.setSubRevenueDesc(subRevenue.getSubRevenueDesc());
            subRevenueResource.setRevenueCode(revenueResource.getRevenueCode());
            subRevenueResource.setPaymentOptions(subRevenue.getPaymentOptions());
            subRevenueResource.setRevenueResource(revenueResource);
            subRevenueResource.setCreatedAt(date);
            subRevenueResource.setUpdatedAt(date);

            return subRevenueSourceRepository.save(subRevenueResource);
        } else {
            throw new RuntimeException("RevenueResource with ID " + subRevenue.getRevenueResource().getId() + " not found");
        }
    }

    public Optional<SubRevenueResource> subRevenueSourceById(Long id) {
        return subRevenueSourceRepository.findById(id);
    }

    public List<SubRevenueResource> subRevenueResourceAll() {
        return  subRevenueSourceRepository.findAll();
    }

}
