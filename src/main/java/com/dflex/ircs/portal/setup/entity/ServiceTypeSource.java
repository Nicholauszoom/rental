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
 * 
 * @author Augustino Mwageni
 *
 *         Entity class for database table tab_service_type_source
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tab_service_type_source")
public class ServiceTypeSource extends CommonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "service_type_source_id_generator")
	@SequenceGenerator(name = "service_type_source_id_generator", sequenceName = "seq_service_type_source", initialValue = 1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
	private Long id;
	
	@GeneratedValue
	@UuidGenerator
	@Column(name="service_type_source_uid",nullable = false)
	private UUID serviceTypeSourceUid;
	
	@PrePersist
    protected void onCreate() {
        setServiceTypeSourceUid(java.util.UUID.randomUUID());
	}

	@Column(name = "is_fixed_amount")
	private Boolean isFixedAmount;

	@Column(name = "fixed_amount")
	private BigDecimal fixedAmount;

	@Column(name = "service_target")
	private BigDecimal serviceTarget;

	@Column(name = "financial_year_id", nullable = false)
	private Long financialYearId;
	
	@Column(name = "record_status_id", nullable = false)
	private Long recordStatusId = 1L;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "service_department_id", nullable = false)
	private ServiceDepartment serviceDepartment;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "service_type_id", nullable = false)
	private ServiceType serviceType;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "currency_id")
	private Currency currency;

}
