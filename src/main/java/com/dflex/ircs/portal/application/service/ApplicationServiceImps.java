package com.dflex.ircs.portal.application.service;

import com.dflex.ircs.portal.application.entity.Application;
import com.dflex.ircs.portal.application.entity.ApplicationDetails;
import com.dflex.ircs.portal.application.entity.ApprovalWorkFlow;
import com.dflex.ircs.portal.application.entity.ApprovalWorkFlowItem;
import com.dflex.ircs.portal.application.repository.ApplicationDetailsRepository;
import com.dflex.ircs.portal.application.repository.ApplicationRepository;
import com.dflex.ircs.portal.application.repository.ApprovalWorkFlowRepository;
import com.dflex.ircs.portal.application.repository.WorkFlowItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public Application createApplication(Application application){
        return  applicationRepository.save(application);
    }

    @Override
    public Optional<Application> findApplicationById(Long id){
        return applicationRepository.findById(id);
    }


    public ApplicationDetails addAppDettails(ApplicationDetails details){
        return applicationDetailsRepository.save(details);
    }

    @Override
    public Optional<ApplicationDetails> findAppDetailById(Long id){
        return applicationDetailsRepository.findById(id);
    }

    public ApprovalWorkFlow addAppWork(ApprovalWorkFlow approvalWorkFlow){
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
