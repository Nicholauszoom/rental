package com.dflex.ircs.portal.payment.api.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@ToString
@XmlRootElement(name = "ircs")
public class PaymentValidationDTO {

    @XmlElement(name = "payment")
    private PaymentDTO payment;

    @XmlElement(name = "hash")
    private String hash;


}