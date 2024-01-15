package com.dflex.ircs.portal.payment.api.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;

import java.util.List;

public class PaymentDTO {

    private PaymentHeaderDTO paymenthdr;
    private List<PaymentDetailDTO> paymentdtls;

    @XmlElement(name = "paymenthdr")
    public PaymentHeaderDTO getPaymenthdr() {
        return paymenthdr;
    }

    public void setPaymenthdr(PaymentHeaderDTO paymenthdr) {
        this.paymenthdr = paymenthdr;
    }

    @XmlElementWrapper(name = "paymentdtls")
    @XmlElement(name = "paymentdtl")
    public List<PaymentDetailDTO> getPaymentdtls() {
        return paymentdtls;
    }

    public void setPaymentdtls(List<PaymentDetailDTO> paymentdtls) {
        this.paymentdtls = paymentdtls;
    }
}