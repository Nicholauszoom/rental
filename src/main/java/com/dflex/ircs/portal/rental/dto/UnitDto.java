package com.dflex.ircs.portal.rental.dto;

import jakarta.persistence.Column;
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
public class UnitDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private  Long id;
    private String unitUid;
    private String unitNumber;
    private String unitName;
    private String unitSize;
    private String typeSize;
    private String status;
    private String propertyNumber;
//    private Long statusId;

}