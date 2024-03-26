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
 *         Entity class for database table tab_other_service_institution
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "tab_other_service_institution")
public class OtherServiceInstitution extends CommonEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "other_service_institution_id_generator")
	@SequenceGenerator(name = "other_service_institution_id_generator", sequenceName = "seq_other_service_institution", initialValue = 1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
	private Long id;
	
	@GeneratedValue
	@UuidGenerator
	@Column(name="other_service_institution_uid",nullable = false)
	private UUID otherServiceInstitutionUid;



	@PrePersist
    protected void onCreate() {
        setOtherServiceInstitutionUid(java.util.UUID.randomUUID());
	}
	
	@Column(name="institution_code",nullable=false,length = 20)
	private String institutionCode;
	
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


	public OtherServiceInstitution(UUID createdBy, String createdByUserName, String institutionCode,
								   String postalAddress, String physicalAddress, String institutionName,
								   String secondaryPhoneNumber, String primaryPhoneNumber, String email,
								   Long recordStatusId) {
		super(createdBy, createdByUserName);
		this.institutionCode = institutionCode;
		this.institutionName = institutionName;
		this.postalAddress = postalAddress;
		this.physicalAddress = physicalAddress;
		this.primaryPhoneNumber = primaryPhoneNumber;
		this.secondaryPhoneNumber = secondaryPhoneNumber;
		this.email = email;
		this.recordStatusId = recordStatusId;
	}
	
}