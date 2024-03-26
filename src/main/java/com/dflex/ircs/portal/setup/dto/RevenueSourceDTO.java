package com.dflex.ircs.portal.setup.dto;

import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@XmlRootElement(name = "revenueSource")
@XmlAccessorType(jakarta.xml.bind.annotation.XmlAccessType.FIELD)
public class RevenueSourceDTO {

    @XmlElement(name = "revenueCode")
    private String revenueCode;

    @XmlElement(name = "revenueDesc")
    private String revenueDesc;

    @XmlElement(name = "unitCode")
    private String unitCode;

    @XmlElement(name = "institutionCode")
    private String institutionCode;

    @XmlElement(name = "createdAt")
    private String createdAt;

    @XmlElement(name = "updateAt")
    private String updatedAt;

}
