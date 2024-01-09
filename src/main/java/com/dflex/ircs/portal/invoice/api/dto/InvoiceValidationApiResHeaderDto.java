package com.dflex.ircs.portal.invoice.api.dto;

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
@XmlRootElement(name = "invoicehdr")
@XmlAccessorType(XmlAccessType.FIELD)
public class InvoiceValidationApiResHeaderDto {
	
	@XmlElement(name = "responseid")
	private String responseIdentity;
	
	@XmlElement(name = "requestid")
	private String requestIdentity;
	
	@XmlElement(name = "paymentnumber")
	private String paymentNumber;
	
	@XmlElement(name = "invoicetype")
	private String invoiceType;
	
	@XmlElement(name = "dtlcount")
	private String detailCount;
	
	@XmlElement(name = "responsestatus")
	private String responseStatus;
	
	@XmlElement(name = "responsestatusdescription")
	private String responseStatusDescription;
	
}
