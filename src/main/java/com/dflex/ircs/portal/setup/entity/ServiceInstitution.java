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
 *         Entity class for database table tab_service_institution
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "tab_service_institution")
public class ServiceInstitution extends CommonEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "service_institution_id_generator")
	@SequenceGenerator(name = "service_institution_id_generator", sequenceName = "seq_service_institution", initialValue = 1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
	private Long id;
	
	@GeneratedValue
	@UuidGenerator
	@Column(name="service_institution_uid",nullable = false)
	private UUID serviceInstitutionUid;



	@PrePersist
    protected void onCreate() {
        setServiceInstitutionUid(java.util.UUID.randomUUID());
	}

	@Column(name="institution_code",nullable=false,length = 20)
	private String institutionCode;
	
	@Column(name="institution_number",nullable=true,length = 20)
	private String institutionNumber;
	
	@Column(name="institution_name",nullable=false,length = 100)
	private String institutionName;

	@Column(name="postal_address",length = 200)
	private String postalAddress;
	
	@Column(name="physical_address",length = 200)
	private String physicalAddress;
	
	@Column(name="primary_phone_number",length = 12)
	private String primaryPhoneNumber;
	
	@Column(name="secondary_phone_number",length = 12)
	private String secondaryPhoneNumber;
	
	@Column(name="email",length = 50)
	private String email;
	
	@Column(name = "record_status_id", nullable = false)
	private Long recordStatusId = 1L;
	
	@ManyToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name="service_institution_category_id" , nullable=false)
	private ServiceInstitutionCategory serviceInstitutionCategory;

//	public ServiceInstitution(UUID createdBy, String createdByUserName, String serviceInstitutionUid, String institutionCode,
//							  String institutionNumber, String postalAddress, String institutionName,
//							  String physicalAddress, String secondaryPhoneNumber,
//							  String primaryPhoneNumber, String email, Long id, Long recordStatusId) {
//		super(createdBy, createdByUserName);
//		this.id = id;
//		this.serviceInstitutionUid = UUID.fromString(serviceInstitutionUid);
//		this.institutionCode = institutionCode;
//		this.institutionNumber = institutionNumber;
//		this.institutionName = institutionName;
//		this.postalAddress = postalAddress;
//		this.physicalAddress = physicalAddress;
//		this.primaryPhoneNumber = primaryPhoneNumber;
//		this.secondaryPhoneNumber = secondaryPhoneNumber;
//		this.email = email;
//		this.recordStatusId = recordStatusId;
//		this.serviceInstitutionCategory = serviceInstitutionCategory;
//	}

	public ServiceInstitution(UUID createdBy, String createdByUserName, String institutionCode,
							  String institutionNumber, String postalAddress, String physicalAddress,
							  String institutionName, String secondaryPhoneNumber, String primaryPhoneNumber, String email,
							  Long id, Long recordStatusId) {


		super(createdBy, createdByUserName);
		this.id = id;
		this.institutionCode = institutionCode;
		this.institutionNumber = institutionNumber;
		this.institutionName = institutionName;
		this.postalAddress = postalAddress;
		this.physicalAddress = physicalAddress;
		this.primaryPhoneNumber = primaryPhoneNumber;
		this.secondaryPhoneNumber = secondaryPhoneNumber;
		this.email = email;
		this.recordStatusId = recordStatusId;
	}


	
}