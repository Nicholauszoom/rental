package com.dflex.ircs.portal.rental.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BuildingDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String buildingUid;

    private String propertyNumber;

    private String propertyName;

    private String blockNumber;

    private String plotNumber;

    private String location;

    private String propertyRegion;

    private String propertyDistrict;

    private String propertyArea;

    private double propertySize;
}
