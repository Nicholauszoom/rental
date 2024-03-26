package com.dflex.ircs.portal.application.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Augustino Mwageni
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AppliedServiceCostingDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String appliedServiceCostingUid;
	
	private Long referenceApplicationId;

	private String referenceApplicationTable;
	
	private String serviceTypeCode;
	
	private String serviceTypeName;
	
	private Long revenueSourceId;
	
	private BigDecimal unitCost;
	
	private Integer unitCount;
	
	private BigDecimal totalCost;
	
	private Long currencyId;
	
	private String currencyCode;
	
	private Double exchangeRate;
	
	private BigDecimal amountPaid;
	
	private String paymentStatus;
	
}
