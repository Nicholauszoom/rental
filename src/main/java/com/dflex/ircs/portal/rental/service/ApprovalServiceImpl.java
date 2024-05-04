package com.dflex.ircs.portal.rental.service;

import com.dflex.ircs.portal.application.entity.Applicant;
import com.dflex.ircs.portal.application.repository.ApplicantRepository;
import com.dflex.ircs.portal.invoice.entity.Invoice;
import com.dflex.ircs.portal.invoice.repository.InvoiceRepository;

import com.dflex.ircs.portal.rental.entity.RentalApplication;
import com.dflex.ircs.portal.rental.repository.RentalApplicationRepository;
import com.dflex.ircs.portal.util.Status;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.management.InvalidApplicationException;
import java.util.UUID;

@Service
public class ApprovalServiceImpl implements ApprovalService{

    private final RentalApplicationRepository rentalApplicationRepository;

    private final RentalInvoiceService rentalInvoiceService;

    private final ApplicantRepository applicantRepository;

    private  final InvoiceRepository invoiceRepository;

    public ApprovalServiceImpl(RentalApplicationRepository rentalApplicationRepository,
                               RentalInvoiceService rentalInvoiceService,
                               ApplicantRepository applicantRepository,
                               InvoiceRepository invoiceRepository) {
        this.rentalApplicationRepository = rentalApplicationRepository;
        this.rentalInvoiceService = rentalInvoiceService;
        this.applicantRepository = applicantRepository;
        this.invoiceRepository = invoiceRepository;
    }

    @Override
    public void approveApplication(Long rentalApplicationId,
                                   String approverName) throws Exception {
        RentalApplication rentalApplication =
                rentalApplicationRepository.findById(rentalApplicationId)
                        .orElseThrow(()->new RuntimeException
                                ("fail to get rental application with id: "
                                        + rentalApplicationId));

        if(rentalApplication.getStatus() != Status.PENDING_APPROVAL){
            throw  new Exception("Application is not pending approval");
        }

        //validation
        rentalApplication.setStatus(Status.APPROVED);
        rentalApplication.setApproverName(approverName);
        rentalApplicationRepository.save((rentalApplication));

        String paymentNumber = rentalInvoiceService.generateRentalPaymentNumber();

        //create invoice with unique payment_number
        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber("INV-" + UUID.randomUUID().toString());
        invoice.setPaymentNumber(paymentNumber);
        invoice.setInvoiceAmount(rentalApplication.getAmount());

        Applicant customer = applicantRepository.findById(rentalApplication
                .getApplicant().getId()).orElseThrow(() ->
                new RuntimeException("cannot find payer with id:"
                        + rentalApplication.getApplicant().getId()));

        invoice.setCustomerName(customer.getApplicantName());
        invoice.setCustomerIdentity(customer.getIdentityNumber());
        invoice.setCustomerAccount(customer.getApplicantAccount());
        invoice.setCustomerName(customer.getIdentityNumber());
        invoice.setInvoiceIssueDate(null);
        invoice.setInvoiceExpiryDate(null);
        invoice.setIssuedBy(approverName);
        invoice.setApprovedBy(approverName);
        invoice.setPaymentOptionId(null);
        invoice.setPaymentOptionName(null);
        invoice.setPaidAmount(rentalApplication.getAmount());
        invoice.setMinimumPaymentAmount(rentalApplication.getAmount());
        invoice.setExchangeRateValue(null);
        invoice.setIsInvoicePaid(null);
        invoice.setInvoicePayPlanId(null);
        invoice.setInvoicePayPlanName(null);
        invoice.setReminderStatusId(null);
        invoice.setDetailCount(null);

        invoiceRepository.save(invoice);







    }
}
