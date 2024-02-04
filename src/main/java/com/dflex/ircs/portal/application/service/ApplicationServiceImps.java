package com.dflex.ircs.portal.application.service;

import com.dflex.ircs.portal.application.entity.Application;
import com.dflex.ircs.portal.application.entity.ApplicationDetails;
import com.dflex.ircs.portal.application.entity.ApprovalWorkFlow;
import com.dflex.ircs.portal.application.entity.ApprovalWorkFlowItem;
import com.dflex.ircs.portal.application.repository.ApplicationDetailsRepository;
import com.dflex.ircs.portal.application.repository.ApplicationRepository;
import com.dflex.ircs.portal.application.repository.ApprovalWorkFlowRepository;
import com.dflex.ircs.portal.application.repository.WorkFlowItemRepository;
import com.dflex.ircs.portal.payer.entity.Payer;
import com.dflex.ircs.portal.payer.service.PayerServiceImpl;
import com.dflex.ircs.portal.revenue.entity.SubRevenueResource;
import com.dflex.ircs.portal.revenue.service.RevenueSourceImps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ApplicationServiceImps implements ApplicationService{

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ApplicationDetailsRepository applicationDetailsRepository;

    @Autowired
    private ApprovalWorkFlowRepository approvalWorkFlowRepository;

    @Autowired
    private WorkFlowItemRepository workFlowItemRepository;

    @Autowired
    private PayerServiceImpl payerService;

    @Autowired
    private RevenueSourceImps source;

    Date date = new Date();
    public Application createApplication(Application application){
        Optional<Payer> payerOptional = payerService.findByPayerId(application.getPayer().getId());
        Optional<SubRevenueResource> subRevenueOptional = source.findSubById(application.getSubRevenueResource().getId());

        if (payerOptional.isPresent() && subRevenueOptional.isPresent()) {
            Payer payer = payerOptional.get();
            SubRevenueResource subRevenue = subRevenueOptional.get();

            Application newApplication = new Application();
            newApplication.setPayerCode(payer.getCode());
            newApplication.setApplicationCode(application.getApplicationCode());
            newApplication.setApplicationDate(application.getApplicationDate());
            newApplication.setApplicationNumber(application.getApplicationNumber());
            newApplication.setApplicationStatus(application.getApplicationStatus());
            newApplication.setSubRevenueCode(subRevenue.getSubRevenueCode());
            newApplication.setSubRevenueResource(subRevenue);
            newApplication.setPayer(payer);
            newApplication.setCreatedAt(date);
            newApplication.setUpdatedAt(date);

            return applicationRepository.save(newApplication);
        } else {
            // Handle the case where either Payer or SubRevenueResource is not found

            throw new RuntimeException("Payer or SubRevenueResource not found");
        }
    }

     @Override
     public Application getAllApplication(){
        return  applicationRepository.findAll().get(0);
     }
    @Override
    public Optional<Application> findApplicationById(Long id){
        return applicationRepository.findById(id);
    }

    public ApplicationDetails addAppDetails(ApplicationDetails AppDetails){
        return applicationDetailsRepository.save(AppDetails);
    }

    @Override
    public Optional<ApplicationDetails> findAppDetailById(Long id){
        return applicationDetailsRepository.findById(id);
    }

    public ApprovalWorkFlow addApprovalWork(ApprovalWorkFlow approvalWorkFlow){
        return approvalWorkFlowRepository.save(approvalWorkFlow);
    }
    @Override
    public Optional<ApprovalWorkFlow> approvalWorkFlowFindById(Long id){
        return approvalWorkFlowRepository.findById(id);
    }


    public ApprovalWorkFlowItem addWorkFlowItem (ApprovalWorkFlowItem item){
        return workFlowItemRepository.save(item);

    }

    @Override
    public  Optional<ApprovalWorkFlowItem> workFlowItemFindByID(Long id){
        return workFlowItemRepository.findById(id);
    }
}
