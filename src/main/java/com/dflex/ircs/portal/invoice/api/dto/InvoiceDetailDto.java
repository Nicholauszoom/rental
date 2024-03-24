package com.dflex.ircs.portal.invoice.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
public class InvoiceDetailDto implements Serializable {
  
	private static final long serialVersionUID = 1L;
	
	private String serviceInstitutionCode;
	
	private String serviceInstitutionName;

	private String invoiceReference;

	private String invoicePaymentNumber;

	private String paymentNumber;

	private String customerName;

	private String customerPhoneNumber;

	private String customerEmail;

	private String invoiceDescription;

	private BigDecimal invoiceAmount;

	private BigDecimal invoiceMinPayAmount;

	private String currencyCode;

	private Date invoiceExpiryDate;

	private Date invoiceIssuedDate;

	private Long billPaymentOption;

	private String billPaymentOptionName;
	
	private String workStationCode;
	
	private String workStationName;
	
	private String applicationNumber;
	
	private String paymentOptionName;
	
	private Long paymentOptionId;

	private List<InvoiceServiceDetailDto> serviceDetails;

}
