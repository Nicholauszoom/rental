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
 *         Entity class for database table tab_service_department
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "tab_service_department")
public class ServiceDepartment extends CommonEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "service_department_id_generator")
	@SequenceGenerator(name = "service_department_id_generator", sequenceName = "seq_service_department", initialValue = 1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
	private Long id;
	
	@GeneratedValue
	@UuidGenerator
	@Column(name="service_department_uid",nullable = false)
	private UUID serviceDepartmentUid;
	
	@PrePersist
    protected void onCreate() {
        setServiceDepartmentUid(java.util.UUID.randomUUID());
	}
	
	@Column(name="department_code",nullable=false,length = 20)
	private String departmentCode;

	@Column(name="department_name",nullable=false,length = 100)
	private String departmentName;
	
	@Column(name="service_institution_id" , nullable=false)
	private Long serviceInstitutionId;
	
	@Column(name = "record_status_id", nullable = false)
	private Long recordStatusId = 1L;
}