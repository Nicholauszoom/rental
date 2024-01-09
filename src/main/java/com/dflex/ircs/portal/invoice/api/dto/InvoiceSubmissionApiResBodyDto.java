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
@XmlRootElement(name = "invoice")
@XmlAccessorType(XmlAccessType.FIELD)
public class InvoiceSubmissionApiResBodyDto {
	
	@XmlElement(name = "invoicehdr")
	private InvoiceSubmissionApiResHeaderDto invoiceHeader;
	
	@XmlElement(name = "invoicedtls")
	private InvoiceSubmissionApiResDetailsDto invoiceDetail;
	
}
