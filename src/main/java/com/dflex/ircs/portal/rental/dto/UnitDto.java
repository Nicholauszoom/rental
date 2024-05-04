package com.dflex.ircs.portal.rental.dto;

import com.dflex.ircs.portal.payer.entity.Payer;
import com.dflex.ircs.portal.rental.entity.Building;
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

    private String propertyNumber;

    private Long buildingId;

    private Long payer;

    private Long statusId;

}
