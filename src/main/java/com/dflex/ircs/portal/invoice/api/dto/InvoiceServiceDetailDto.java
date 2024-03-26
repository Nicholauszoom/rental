package com.dflex.ircs.portal.invoice.api.dto;

import java.io.Serializable;
import java.math.BigDecimal;

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
public class InvoiceServiceDetailDto implements Serializable {
  
	private static final long serialVersionUID = 1L;

	private String serviceTypeCode;

	private String serviceTypeName;
	
	private Integer totalUnits;

	private BigDecimal serviceAmount;
  
}
