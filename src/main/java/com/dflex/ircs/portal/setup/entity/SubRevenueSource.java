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
 *         Entity class for database table tab_sub_revenue_source
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tab_sub_revenue_source")
public class SubRevenueSource extends CommonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sub_revenue_source_id_generator")
	@SequenceGenerator(name = "sub_revenue_source_id_generator", sequenceName = "seq_sub_revenue_source", initialValue = 1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
	private Long id;
	
	@Column(name = "main_revenue_source_id")
	private Long mainRevenueSourceId;
	
	@Column(name = "record_status_id", nullable = false)
	private Long recordStatusId = 1L;
	
	@ManyToOne(optional=false,fetch = FetchType.EAGER)
	@JoinColumn(name = "sub_revenue_source_id")
	private RevenueSource subRevenueSource;

	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "sub_revenue_source_type_id", nullable = false)
	private SubRevenueSourceType subRevenueSourceType;

}
