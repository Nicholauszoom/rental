package com.dflex.ircs.portal.license.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author Augustino Mwageni
 * 
 * Data Transformation Class for Database Table tab_license
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class LicenseDto implements  Serializable{

	private static final long serialVersionUID = 1L;

    private Long id;
    
    private String licenseUid;
	
	private Long detailReferenceId;
	
	private String detailReferenceTable;
	
	private Long parentLicenseId;
	
	private Long licenseTypeId;
	
	private String licenseTypeName;
	
	private String licenseNumber;
	
	private String licenseName;
	
	private String ownerUid;
	
	private String ownerName;
	
	private String ownerAccount;
	
	private String ownerIdentityNumber;
	
	private Date issuedDate;
	
	private Date commencedDate;
	
	private Integer  tenure;
	
	private Date expiryDate;
	
	private Long licenseStatusId;
	
	private String licenseStatusName;
	
	private Boolean hasRenewRequest;
	
	private Boolean hasTransferRequest;
	
	private Boolean isPaid;

	private Long previousLicenseId;

	private String mobileNumber;

	private String emailAdress;

	private Long applicantId;

}
