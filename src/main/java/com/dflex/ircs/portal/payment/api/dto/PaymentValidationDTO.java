package com.dflex.ircs.portal.payment.api.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "ircs")
public class PaymentValidationDTO {

    private PaymentDTO payment;
    private String hash;

    @XmlElement(name = "payment")
    public PaymentDTO getPayment() {return payment;}
    public void setPayment(PaymentDTO payment) {this.payment = payment;}
    public String getHash() {return hash;}
    @XmlElement(name = "hash")
    public void setHash(String hash) {
        this.hash = hash;
    }
}