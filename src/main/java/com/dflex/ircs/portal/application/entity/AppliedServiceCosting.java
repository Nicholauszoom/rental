package com.dflex.ircs.portal.application.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.dflex.ircs.portal.setup.entity.RevenueSource;
import com.dflex.ircs.portal.util.CommonEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Augustino Mwageni
 *
 *         Entity class for database table tab_applied_service_costing
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "tab_applied_service_costing")
public class AppliedServiceCosting extends CommonEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "applied_service_costing_id_generator")
	@SequenceGenerator(name = "applied_service_costing_id_generator", sequenceName = "seq_applied_service_costing", initialValue = 1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
	private Long id;
	
	@GeneratedValue
	@UuidGenerator
	@Column(name="applied_service_costing_uid",nullable = false)
	private UUID appliedServiceCostingUid;
	
	@PrePersist
    protected void onCreate() {
        setAppliedServiceCostingUid(java.util.UUID.randomUUID());
    }

	@Column(name = "reference_application_id", nullable = false)
	private Long referenceApplicationId;

	@Column(name = "reference_application_table", length = 20, nullable = false)
	private String referenceApplicationTable;
	
	@Column(name = "service_type_code", length = 20, nullable = false)
	private String serviceTypeCode;
	
	@Column(name = "service_type_name", length = 200, nullable = false)
	private String serviceTypeName;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "revenue_source_id", nullable = false)
	private RevenueSource revenueSource;
	
	@Column(name = "unit_cost", nullable = false)
	private BigDecimal unitCost;
	
	@Column(name = "unit_count", nullable = false)
	private Integer unitCount = 1;
	
	@Column(name = "total_cost",nullable = false)
	private BigDecimal totalCost;
	
	@Column(name = "currency_id",nullable = false)
	private Long currencyId;
	
	@Column(name = "currency_code",nullable = false, length = 3)
	private String currencyCode;
	
	@Column(name = "exchange_rate",nullable = false)
	private Double exchangeRate = 1.0D;
	
	@Column(name = "record_status_id", nullable = false)
	private Long recordStatusId = 1L;

	/**
	 * @param createdBy
	 * @param createdByUserName
	 * @param referenceApplicationId
	 * @param referenceApplicationTable
	 * @param serviceTypeCode
	 * @param serviceTypeName
	 * @param revenueSource
	 * @param unitCost
	 * @param unitCount
	 * @param totalCost
	 * @param currencyId
	 * @param currencyCode
	 * @param exchangeRate
	 */
	public AppliedServiceCosting(UUID createdBy, String createdByUserName, Long referenceApplicationId,
			String referenceApplicationTable, String serviceTypeCode, String serviceTypeName,
			RevenueSource revenueSource, BigDecimal unitCost, Integer unitCount, BigDecimal totalCost, Long currencyId,
			String currencyCode, Double exchangeRate) {
		super(createdBy, createdByUserName);
		this.referenceApplicationId = referenceApplicationId;
		this.referenceApplicationTable = referenceApplicationTable;
		this.serviceTypeCode = serviceTypeCode;
		this.serviceTypeName = serviceTypeName;
		this.revenueSource = revenueSource;
		this.unitCost = unitCost;
		this.unitCount = unitCount;
		this.totalCost = totalCost;
		this.currencyId = currencyId;
		this.currencyCode = currencyCode;
		this.exchangeRate = exchangeRate;
	}
	

}