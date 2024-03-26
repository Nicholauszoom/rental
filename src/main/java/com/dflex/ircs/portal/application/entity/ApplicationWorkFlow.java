package com.dflex.ircs.portal.application.entity;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.dflex.ircs.portal.util.CommonEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
 *         Entity class for database table tab_application_work_flow
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tab_application_work_flow")
public class ApplicationWorkFlow extends CommonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "application_work_flow_id_generator")
	@SequenceGenerator(name = "application_work_flow_id_generator", sequenceName = "seq_application_work_flow", initialValue = 1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
	private Long id;
	
	@GeneratedValue
	@UuidGenerator
	@Column(name = "application_work_flow_uid", nullable = false)
	private UUID applicationWorkFlowUid;

	@PrePersist
	protected void onCreate() {
		setApplicationWorkFlowUid(java.util.UUID.randomUUID());
	}
	
	@Column(name = "application_id",nullable =false)
	private Long applicationId;
	
	@Column(name = "application_uid",nullable =false)
	private UUID applicationUid;
	
	@Column(name ="work_flow_id",nullable =false)
	private Long workFlowId;

	@Column(name = "work_flow_name",nullable =false,length=50)
	private String workFlowName;
	
	@Column(name ="work_flow_action_id",nullable =false)
	private Long workFlowActionId;

	@Column(name = "work_flow_action_name",nullable =false,length=50)
	private String workFlowActionName;
	
	@Column(name = "work_flow_remark",nullable =true,length=500)
	private String workFlowRemark;

	@Column(name = "record_status_id", nullable = false)
	private Long recordStatusId = 1L;

	/**
	 * @param createdBy
	 * @param createdByUserName
	 * @param applicationId
	 * @param applicationUid
	 * @param workFlowId
	 * @param workFlowName
	 * @param workFlowActionId
	 * @param workFlowActionName
	 * @param workFlowRemark
	 */
	public ApplicationWorkFlow(UUID createdBy, String createdByUserName, Long applicationId, UUID applicationUid,
			Long workFlowId, String workFlowName, Long workFlowActionId, String workFlowActionName,
			String workFlowRemark) {
		super(createdBy, createdByUserName);
		this.applicationId = applicationId;
		this.applicationUid = applicationUid;
		this.workFlowId = workFlowId;
		this.workFlowName = workFlowName;
		this.workFlowActionId = workFlowActionId;
		this.workFlowActionName = workFlowActionName;
		this.workFlowRemark = workFlowRemark;
	}

}
