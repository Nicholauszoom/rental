package com.dflex.ircs.portal.setup.entity;

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
 *         Entity class for database table tab_work_flow_action
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tab_work_flow_action")
public class WorkFlowAction extends CommonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "work_flow_action_id_generator")
	@SequenceGenerator(name = "work_flow_action_id_generator", sequenceName = "seq_work_flow_action", initialValue = 1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
	private Long id;
	
	@GeneratedValue
	@UuidGenerator
	@Column(name = "work_flow_action_uid", nullable = false)
	private UUID workFlowActionUid;

	@PrePersist
	protected void onCreate() {
		setWorkFlowActionUid(java.util.UUID.randomUUID());
	}

	@Column(name = "work_flow_action_name",nullable =false,length=50)
	private String workFlowActionName;
	
	@Column(name = "work_flow_action_description",nullable =true,length=200)
	private String workFlowActionDescription;
	
	@Column(name = "work_flow_id", nullable = false)
	private Long workFlowId;

	@Column(name = "record_status_id", nullable = false)
	private Long recordStatusId = 1L;
	
}
