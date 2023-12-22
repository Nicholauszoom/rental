package com.ircs.portal.invoice.dto;

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
public class InvoiceSubmissionApiResDetailDto {
	
	@XmlElement(name = "invoicenumber")
	private String invoiceNumber;
	
	@XmlElement(name = "invoicepaymentnumber")
	private String invoicePaymentNumber;
	
	@XmlElement(name = "invoicestatus")
	private String invoiceStatus;
	
	@XmlElement(name = "invoicestatusdescription")
	private String invoiceStatusDescription;
	
}
