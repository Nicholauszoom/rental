package com.dflex.ircs.portal.payment.api.dto;

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
@XmlRootElement(name = "paymenthdr")
@XmlAccessorType(XmlAccessType.FIELD)
public class PaymentNotificationApiReqHeaderDto {
	
	@XmlElement(name = "reqid")
	private String requestIdentity;
	
	@XmlElement(name = "paymentfacilitator")
	private String paymentFacilitator;
	
	@XmlElement(name = "dtlcount")
	private Integer detailCount;
	
	@XmlElement(name = "paymentnumber")
	private String paymentNumber;
	
}
