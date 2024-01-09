package com.dflex.ircs.portal.invoice.api.dto;

import java.math.BigDecimal;
import java.util.Date;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author Augustino Mwageni
 *
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@XmlRootElement(name = "invoicedtl")
@XmlAccessorType(XmlAccessType.FIELD)
public class InvoiceSubmissionApiReqDetailDto {
	
	@XmlElement(name = "invoicenumber")
	private String invoiceNumber;
	
	@XmlElement(name = "serviceinstitution")
	private String serviceInstitution;
	
	@XmlElement(name = "workstation")
	private String workStation;
	
	@XmlElement(name = "invoicedescription")
	private String invoiceDescription;
	
	@XmlElement(name = "customerid")
	private String customerIdentity;
	
	@XmlElement(name = "customeridtype")
	private Long customerIdentityType;
	
	@XmlElement(name = "customeraccount")
	private String customerAccount;
	
	@XmlElement(name = "customername")
	private String customerName;
	
	@XmlElement(name = "customerphonenumber")
	private String customerPhoneNumber;
	
	@XmlElement(name = "customeremail")
	private String customerEmail;
	
	@XmlElement(name = "invoiceissuedate")
	private String invoiceIssueDate;
	
	@XmlElement(name = "invoiceexpirydate")
	private String invoiceExpiryDate;
	
	@XmlElement(name = "issuedby")
	private String issuedBy;
	
	@XmlElement(name = "approvedby")
	private String approvedBy;
	
	@XmlElement(name = "invoiceamount")
	private BigDecimal invoiceAmount;
	
	@XmlElement(name = "invoicemaxamount")
	private BigDecimal invoiceMaxAmount;
	
	@XmlElement(name = "invoiceminamount")
	private BigDecimal invoiceMinAmount;
	
	@XmlElement(name = "currency")
	private String currency;
	
	@XmlElement(name = "exchangerate")
	private Double exchangeRate;
	
	@XmlElement(name = "invoiceplan")
	private Long invoicePlan;
	
	@XmlElement(name = "servicedtls")
	private InvoiceSubmissionApiReqServicesDto invoiceServices;
	
}
