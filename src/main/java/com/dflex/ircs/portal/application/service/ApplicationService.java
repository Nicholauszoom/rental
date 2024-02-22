/**package com.dflex.ircs.portal.application.service;

import com.dflex.ircs.portal.application.entity.Application;
import com.dflex.ircs.portal.application.entity.ApplicationDetails;
import com.dflex.ircs.portal.application.entity.ApprovalWorkFlow;
import com.dflex.ircs.portal.application.entity.ApprovalWorkFlowItem;

import java.util.Optional;

public interface ApplicationService {
    Application getAllApplication();

    Optional<Application> findApplicationById(Long id);

    Optional<ApplicationDetails> findAppDetailById(Long id);

    Optional<ApprovalWorkFlow> approvalWorkFlowFindById(Long id);

    Optional<ApprovalWorkFlowItem> workFlowItemFindByID(Long id);
}
**/