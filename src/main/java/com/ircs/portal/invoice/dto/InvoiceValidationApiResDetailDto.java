package com.ircs.portal.invoice.dto;

import java.math.BigDecimal;

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
public class InvoiceValidationApiResDetailDto {
	
	@XmlElement(name = "invoicepaymentnumber")
	private String invoicePaymentNumber;
	
	@XmlElement(name = "serviceinstitution")
	private String serviceInstitution;
	
	@XmlElement(name = "serviceinstitutionname")
	private String serviceInstitutionName;
	
	@XmlElement(name = "invoicedescription")
	private String invoiceDescription;
	
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
	
	@XmlElement(name = "invoicemaxamount")
	private BigDecimal invoiceMaxAmount;
	
	@XmlElement(name = "invoiceminamount")
	private BigDecimal invoiceMinAmount;
	
	@XmlElement(name = "currency")
	private String currency;
	
	@XmlElement(name = "invoiceplan")
	private Long invoicePlan;
	
}
