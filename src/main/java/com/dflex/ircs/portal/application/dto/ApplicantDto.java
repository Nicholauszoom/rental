package com.dflex.ircs.portal.application.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Augustino Mwageni
 *
 *         Data Transformation class for database table tab_applicant
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ApplicantDto  implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String applicantUid;

	private String applicantAccount;
	
	private String applicantName;

	private String identityNumber;

	private Long identityTypeId;
	
	private String identityTypeName;
	
	private String nationality;

	private String telephoneNumber;
	
	private String mobileNumber;
	
	private String whatsappNumber;

	private String emailAddress;
	
	private String postalAddress;
	
	private String plotNumber;
	
	private String street;
	
	private String ward;
	
	private String location;

	private Long genderId;
	
	private String genderName;
	
    private Float locationLatitude;

    private Float locationLongitude;

	private String blockCode;
	
	private String blockNumber;
	
	private Long recordStatusId;
	
}
