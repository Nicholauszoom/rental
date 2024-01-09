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
 *         Entity class for database table tab_service_institution_category
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "tab_service_institution_category")
public class ServiceInstitutionCategory extends CommonEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "service_institution_category_id_generator")
	@SequenceGenerator(name = "service_institution_category_id_generator", sequenceName = "seq_service_institution_category", initialValue = 1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
	private Long id;
	
	@GeneratedValue
	@UuidGenerator
	@Column(name="service_institution_category_uid",nullable = false)
	private UUID serviceInstitutionCategoryUid;
	
	@PrePersist
    protected void onCreate() {
        setServiceInstitutionCategoryUid(java.util.UUID.randomUUID());
	}
	
	@Column(name="service_institution_category_code",nullable=false,length = 20)
	private String serviceInstitutionCategoryCode;

	@Column(name="service_institution_category_name",nullable=false,length = 50)
	private String serviceInstitutionCategoryName;
	
	@Column(name = "record_status_id", nullable = false)
	private Long recordStatusId = 1L;
}