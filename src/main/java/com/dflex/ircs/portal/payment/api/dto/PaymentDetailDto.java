package com.dflex.ircs.portal.payment.api.dto;

import java.math.BigDecimal;
import java.util.Date;

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
public class PaymentDetailDto {
	
	private Long id;
	
	private String paymentUid;
	
	private String transactionNumber;
	
	private String transactionReference;
	
	private String invoiceNumber;
	
	private String paymentNumber;
	
	private String invoicePaymentNumber;
	
	private BigDecimal paymentAmount;
	
	private String currency;
	
	private String collectionAccount;
	
	private Date paymentDate;
	
	private Date receivedDate;
	
	private String paymentMethod;
	
	private String paymentMethodReference;
	
	private String payerName;
	
	private String payerPhoneNumber;
	
	private String payerEmail;
	
	private String paymentFacilitatorCode;
	
	private String paymentFacilitatorName;
}
