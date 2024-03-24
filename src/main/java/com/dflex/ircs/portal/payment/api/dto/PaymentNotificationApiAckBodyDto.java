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
@XmlRootElement(name = "payment")
@XmlAccessorType(XmlAccessType.FIELD)
public class PaymentNotificationApiAckBodyDto {
	
	@XmlElement(name = "ackid")
	private String acknowledgementIdentity;
	
	@XmlElement(name = "reqid")
	private String requestIdentity;
	
	@XmlElement(name = "ackstatus")
	private String acknowledgementStatus;
	
	@XmlElement(name = "ackstatusdescription")
	private String acknowledgementStatusDescription;
	
}
