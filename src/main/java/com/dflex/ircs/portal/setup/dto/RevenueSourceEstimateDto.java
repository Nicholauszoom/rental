package com.dflex.ircs.portal.setup.dto;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * DTO class for database table tab_revenue_source_estimate
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class RevenueSourceEstimateDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String revenueSourceEstimateUid;

	private Long financialYearId;

	private BigDecimal revenueTarget;

	private Long recordStatusId;
	
	private Long currenyId;

	private Long revenueSourceId;

}
