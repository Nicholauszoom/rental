package com.dflex.ircs.portal.setup.dto;

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
 *         DTO class for database table tab_revenue_source
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RevenueSourceDetailsDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String revenueSourceUid;

	private Boolean isFixedAmount;

	private BigDecimal fixedAmount;

	private String appModuleUid;
	
	private String serviceTypeUid;
	
	private String serviceTypeName;
	
	private String serviceTypeCode;

	private String currencyUid;
	
	private String currencyName;
	
	private String currencyCode;
	
	private String workFlowUid;
	
	private Integer workFlowNumber;
	
	private String workFlowName;
	
	private String appFormUid;
	
}
