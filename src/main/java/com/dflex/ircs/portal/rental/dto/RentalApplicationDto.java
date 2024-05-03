package com.dflex.ircs.portal.rental.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RentalApplicationDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String propertyNumber;

    private String unitNumber;

    private Long payerId;

    private String propertyName;

    private String blockNumber;

    private String plotNumber;

    private String location;

    private String propertyRegion;

    private String propertyDistrict;

    private String propertyArea;

    private String priceType;

    private BigDecimal dynamicPrice;

    private BigDecimal fixedPrice;

    private String unitName;

    private String unitSize;

    private String typeSize;




}
