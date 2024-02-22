package com.dflex.ircs.portal.payment.api.dto;


import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
@ToString
@XmlRootElement(name = "paymentDtls")
@XmlAccessorType(jakarta.xml.bind.annotation.XmlAccessType.FIELD)
public class PaymentDetailDto {

    @XmlElement(name = "transactionnumber")
    private String transactionnumber;

    @XmlElement(name = "transactionreference")
    private String transactionreference;

    @XmlElement(name = "serviceinstitution")
    private String serviceinstitution;

    @XmlElement(name = "serviceinstitutionname")
    private String serviceinstitutionname;

    @XmlElement(name = "invoicepaymentnumber")
    private String invoicepaymentnumber;

    @XmlElement(name = "paymentamount")
    private BigDecimal paymentamount;

    @XmlElement(name = "currency")
    private String currency;

    @XmlElement(name = "paymentdate")
    private String paymentdate;

    @XmlElement(name = "paymentmethod")
    private String paymentmethod;

    @XmlElement(name = "payername")
    private String payername;

    @XmlElement(name = "payerphonenumber")
    private String payerphonenumber;

    @XmlElement(name = "payeremail")
    private String payeremail;

}
