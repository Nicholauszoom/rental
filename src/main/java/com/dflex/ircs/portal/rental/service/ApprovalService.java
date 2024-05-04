package com.dflex.ircs.portal.rental.service;

public interface ApprovalService {
    public void approveApplication(
            Long rentalApplicationId,
            String approverName) throws Exception;
}
