package com.dflex.ircs.portal.data.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OtherBillRevenueDto {

    private String serviceType;

    private int units;

    private Double amount;
}
