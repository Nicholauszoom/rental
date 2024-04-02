package com.dflex.ircs.portal.setup.entity;

import java.io.Serializable;
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
 *         Entity class for database table tab_work_flow_cost
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tab_work_flow_cost")
public class WorkFlowCost extends CommonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "work_flow_cost_id_generator")
	@SequenceGenerator(name = "work_flow_cost_id_generator", sequenceName = "seq_work_flow_cost", initialValue = 1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
	private Long id;
	
	@GeneratedValue
	@UuidGenerator
	@Column(name = "work_flow_cost_uid", nullable = false)
	private UUID workFlowCostUid;

	@PrePersist
	protected void onCreate() {
		setWorkFlowCostUid(java.util.UUID.randomUUID());
	}

	@Column(name = "record_status_id", nullable = false)
	private Long recordStatusId = 1L;
	
	@ManyToOne(fetch=FetchType.EAGER,optional=false)
	@JoinColumn(name = "revenue_source_id", nullable = false)
	private RevenueSource revenueSource;

	@ManyToOne(fetch=FetchType.EAGER,optional=false)
	@JoinColumn(name = "revenue_source_work_flow_id", nullable = false)
	private RevenueSourceWorkFlow revenueSourceWorkFlow;
	
}
