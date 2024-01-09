package com.dflex.ircs.portal.invoice.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class InvoiceValidationApiReqBodyDto {
	
	@XmlElement(name = "requestid")
	private String requestIdentity;
	
	@XmlElement(name = "param1") //Invoice Payment Number
	private String param1;
	
	@XmlElement(name = "param2")
	private String param2;
	
	@XmlElement(name = "param3")
	private String param3;
	
	@XmlElement(name = "param4")
	private String param4;
	
	@XmlElement(name = "param5")
	private String param5;
	
}
