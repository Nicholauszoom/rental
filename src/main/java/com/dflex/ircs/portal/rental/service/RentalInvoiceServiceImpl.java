package com.dflex.ircs.portal.rental.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RentalInvoiceServiceImpl implements RentalInvoiceService{
    @Override
    public String generateRentalPaymentNumber() {
        //unique rental payment_number
        return "PAY-" + System.currentTimeMillis() + "-" + UUID.randomUUID().toString();
    }
}
