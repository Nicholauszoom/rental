package com.dflex.ircs.portal.setup.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

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
 * Entity class for database table tab_revenue_source_estimate
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tab_revenue_source_estimate")
public class RevenueSourceEstimate extends CommonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "revenue_source_estimate_id_generator")
	@SequenceGenerator(name = "revenue_source_estimate_id_generator", sequenceName = "seq_revenue_source_estimate", initialValue = 1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
	private Long id;
	
	@GeneratedValue
	@UuidGenerator
	@Column(name = "revenue_source_estimate_uid", nullable = false)
	private UUID revenueSourceEstimateUid;

	@PrePersist
	protected void onCreate() {
		setRevenueSourceEstimateUid(java.util.UUID.randomUUID());
	}
	
	@Column(name = "financial_year_id", nullable = false)
	private Long financialYearId;

	@Column(name = "revenue_target")
	private BigDecimal revenueTarget;

	@Column(name = "record_status_id", nullable = false)
	private Long recordStatusId = 1L;
	
	@ManyToOne(optional = true, fetch = FetchType.LAZY)
	@JoinColumn(name = "currency_id")
	private Currency currency;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "revenue_source_id", nullable = false)
	private RevenueSource revenueSource;

	/**
	 * @param createdBy
	 * @param createdByUserName
	 * @param financialYearId
	 * @param revenueTarget
	 * @param recordStatusId
	 * @param currency
	 * @param revenueSource
	 */
	public RevenueSourceEstimate(UUID createdBy, String createdByUserName, Long financialYearId,
			BigDecimal revenueTarget, Long recordStatusId, Currency currency, RevenueSource revenueSource) {
		super(createdBy, createdByUserName);
		this.financialYearId = financialYearId;
		this.revenueTarget = revenueTarget;
		this.recordStatusId = recordStatusId;
		this.currency = currency;
		this.revenueSource = revenueSource;
	}
	
}
