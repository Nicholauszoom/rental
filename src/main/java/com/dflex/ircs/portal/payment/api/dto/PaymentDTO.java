package com.dflex.ircs.portal.payment.api.dto;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter @Getter
@XmlRootElement(name = "payment")
@XmlAccessorType(jakarta.xml.bind.annotation.XmlAccessType.FIELD)
public class PaymentDTO {

    @XmlElement(name = "paymenthdr")
    private PaymentHeaderDTO paymenthdr;

    @XmlElementWrapper(name = "paymentdtls")
    @XmlElement(name = "paymentdtl")
    private List<PaymentDetailDTO> paymentdtls;





}