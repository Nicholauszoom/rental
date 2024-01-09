package com.dflex.ircs.portal.invoice.api.dto;

import java.util.List;

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
@XmlRootElement(name = "invoicedtls")
@XmlAccessorType(XmlAccessType.FIELD)
public class InvoiceSubmissionApiReqDetailsDto {
	
	@XmlElement(name = "invoicedtl")
	private List<InvoiceSubmissionApiReqDetailDto> invoiceDetailList;
}
