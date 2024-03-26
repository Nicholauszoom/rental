package com.dflex.ircs.portal.auth.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Augustino Mwageni
 * 
 * DTO Class for Database Table tab_communication_api
 *
 */

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper=false)
public class CommunicationApiDetailsDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String apiDescription;

	private Boolean applyDigitalSignature;

	private String apiUrl;
	
	private Long apiUrlStatusId;

	private Long inboundStatusId;
	
	private Long outboundStatusId;
	
	private Long apiStatusId;
	
	private Long apiCategoryId;
	
	private Long apiVersionNumber;
	
	private String clientKey;
	
	private Long systemStatusId;

	private String certificateAlias;
	
	private String certificatePassphrase;
	
	private String certificateFilename;
	
	private String certificateSerialNumber;
	
	private Long certificateStatusId;
	
	private String certificateAlgorithm;
	
	private String systemClientCode;
	
	private Long systemClientStatusId;
	
	private String internalCertificateAlias;
	
	private String internalCertificatePassphrase;
	
	private String internalCertificateFilename;
	
	private String internalCertificateSerialNumber;
	
	private Long signatureAlgo;
	
}