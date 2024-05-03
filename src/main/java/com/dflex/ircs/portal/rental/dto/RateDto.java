package com.dflex.ircs.portal.rental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RateDto {

    private String unitNumber;
    private String propertyNumber;
    private String priceType;
    private BigDecimal dynamicPrice;
    private BigDecimal fixedPrice;

}
