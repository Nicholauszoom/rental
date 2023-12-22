package com.ircs.portal.invoice.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ircs.portal.setup.entity.ServiceTypeSource;

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
@XmlRootElement(name = "servicedtl")
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceSubmissionApiReqServiceDto {
	
	@XmlElement(name = "servicedepartment")
	private String serviceDepartment;
	
	@XmlElement(name = "servicetype")
	private String serviceType;
	
	@XmlElement(name = "servicereference")
	private String serviceReference;
	
	@XmlElement(name = "serviceamount")
	private BigDecimal serviceAmount;
	
	private ServiceTypeSource serviceTypeSource;
}
