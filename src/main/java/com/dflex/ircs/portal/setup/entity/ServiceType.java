package com.dflex.ircs.portal.setup.entity;

import java.io.Serializable;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.dflex.ircs.portal.util.CommonEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

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
 *         Entity class for database table tab_service_type
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tab_service_type")
public class ServiceType extends CommonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "service_type_id_generator")
	@SequenceGenerator(name = "service_type_id_generator", sequenceName = "seq_service_type", initialValue = 1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
	private Long id;
	
	@GeneratedValue
	@UuidGenerator
	@Column(name="service_type_uid",nullable = false)
	private UUID serviceTypeUid;
	
	@PrePersist
    protected void onCreate() {
        setServiceTypeUid(java.util.UUID.randomUUID());
	}
	
	@Column(name = "service_type_code", nullable = false, length = 10)
	private String serviceTypeCode;

	@Column(name = "service_type_description", nullable = false, length = 100)
	private String serviceTypeDescription;

	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name = "parent_service_type_id", nullable = true)
	//@JsonManagedReference
	@JsonBackReference
	private ServiceType parentServiceType;

	@Column(name = "record_status_id", nullable = false)
	private Long recordStatusId = 1L;
}