package com.dflex.ircs.portal.data.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;



/**
 *
 * @author Mwamba Mwendavano 04/2024
 *
 */



@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class OtherBillProcessingDto {

    private static final long serialVersionUID = 1L;

    private Long  applicantId;

    private String applicantName;

    private Long identityType;

    private String identityNumber;

    private String mobileNumber;

    private String email;

    private String nationality;

    private String applicantAccount;

    private String currency;


    private String serviceType;

    private Long revenueSourceId;

    private String amount;

    private String serviceDepartment;

    private String description;



}
