package com.dflex.ircs.portal.payment.api.dto;

import java.math.BigDecimal;
import java.util.List;

import com.dflex.ircs.portal.invoice.entity.Invoice;

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
@XmlRootElement(name = "paymentdtl")
@XmlAccessorType(XmlAccessType.FIELD)
public class PaymentNotificationApiReqDetailDto {
	
	@XmlElement(name = "transactionnumber")
	private String transactionNumber;
	
	@XmlElement(name = "serviceinstitution")
	private String serviceInstitution;
	
	@XmlElement(name = "transactionreference")
	private String transactionReference;
	
	@XmlElement(name = "invoicenumber")
	private String invoiceNumber;
	
	@XmlElement(name = "invoicepaymentnumber")
	private String invoicePaymentNumber;
	
	@XmlElement(name = "paymentamount")
	private BigDecimal paymentAmount;
	
	@XmlElement(name = "currency")
	private String currency;
	
	@XmlElement(name = "collectionaccount")
	private String collectionAccount;
	
	@XmlElement(name = "paymentdate")
	private String paymentDate;
	
	@XmlElement(name = "receiveddate")
	private String receivedDate;
	
	@XmlElement(name = "paymentmethod")
	private String paymentMethod;
	
	@XmlElement(name = "paymentmethodreference")
	private String paymentMethodReference;
	
	@XmlElement(name = "payername")
	private String payerName;
	
	@XmlElement(name = "payerphonenumber")
	private String payerPhoneNumber;
	
	@XmlElement(name = "payeremail")
	private String payerEmail;
	
	private List<String> processStatus;
	
	private List<String> processStatusDescription;
	
	private Boolean isPaid;
	
	private Long collectionAccountId;
	
	private Invoice invoice;
	
	private Long paymentFacilitatorId;
}
