package com.dflex.ircs.portal.setup.entity;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.dflex.ircs.portal.module.entity.AppForm;
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
 *         Entity class for database table tab_revenue_source_work_flow
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tab_revenue_source_work_flow")
public class RevenueSourceWorkFlow extends CommonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "revenue_source_work_flow_id_generator")
	@SequenceGenerator(name = "revenue_source_work_flow_id_generator", sequenceName = "seq_revenue_source_work_flow", initialValue = 1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
	private Long id;
	
	@GeneratedValue
	@UuidGenerator
	@Column(name = "revenue_source_work_flow_uid", nullable = false)
	private UUID revenueSourceWorkFlowUid;

	@PrePersist
	protected void onCreate() {
		setRevenueSourceWorkFlowUid(java.util.UUID.randomUUID());
	}

	@Column(name = "work_flow_number",nullable =false)
	private Integer workFlowNumber;
	
	@Column(name = "is_default_work_flow",nullable =true,length=200)
	private Boolean isDefaultWorkFlow;

	@Column(name = "record_status_id", nullable = false)
	private Long recordStatusId = 1L;
	
	@Column(name = "revenue_source_id", nullable = false)
	private Long revenueSourceId;
	
	@Column(name = "work_flow_id", nullable = false)
	private Long workFlowId;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "app_form_id", nullable = true)
	private AppForm appForm;

}
