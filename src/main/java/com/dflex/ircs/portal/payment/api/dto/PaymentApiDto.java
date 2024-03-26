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
public class PaymentApiDto {

    @XmlElement(name = "paymenthdr")
    private PaymentHeaderApiDto paymenthdr;

    @XmlElementWrapper(name = "paymentdtls")
    @XmlElement(name = "paymentdtl")
    private List<PaymentValidationDetailDto> paymentdtls;

}