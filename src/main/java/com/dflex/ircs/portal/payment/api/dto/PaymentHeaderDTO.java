package com.dflex.ircs.portal.payment.api.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Getter @Setter
@XmlRootElement(name = "paymentHr")
@XmlAccessorType(XmlAccessType.FIELD)
public class PaymentHeaderDTO {

    @XmlElement(name = "ackid")
    private String ackid;

    @XmlElement(name = "requestid")
    private String requestid;

    @XmlElement(name = "dtlcount")
    private int dtlcount;

    @XmlElement(name = "paymentnumber")
    private String paymentnumber;


}
