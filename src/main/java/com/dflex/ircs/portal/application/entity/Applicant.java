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
import jakarta.persistence.JoinColumn;
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
 *         Entity class for database table tab_applicant
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tab_applicant")
public class Applicant extends CommonEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "applicant_id_generator")
	@SequenceGenerator(name = "applicant_id_generator", sequenceName = "seq_applicant", initialValue = 1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
	private Long id;
	
	@GeneratedValue
	@UuidGenerator
	@Column(name = "applicant_uid", nullable = false)
	private UUID applicantUid;

	@PrePersist
	protected void onCreate() {
		setApplicantUid(java.util.UUID.randomUUID());
	}
	
	@Column(name = "applicant_account", length = 50, nullable = false)
	private String applicantAccount;
	
	@Column(name = "applicant_name", length = 200, nullable = false)
	private String applicantName;

	@Column(name = "identity_number", length = 50, nullable = false)
	private String identityNumber;

	@JoinColumn(name = "identity_type_id", nullable = false)
	private Long identityTypeId;
	
	@Column(name = "nationality", length = 50, nullable = false)
	private String nationality;

	@Column(name = "telephone_number", length = 50,nullable = true)
	private String telephoneNumber;
	
	@Column(name = "mobile_number", length = 50,nullable = true)
	private String mobileNumber;
	
	@Column(name = "whatsapp_number", length = 50,nullable = true)
	private String whatsappNumber;

	@Column(name = "email_address", length = 50,nullable = true)
	private String emailAddress;
	
	@Column(name = "postal_address", length = 50,nullable = true)
	private String postalAddress;
	
	@Column(name = "plot_number", length = 50,nullable = true)
	private String plotNumber;
	
	@Column(name = "street", length = 50,nullable = true)
	private String street;
	
	@Column(name = "Ward", length = 50,nullable = true)
	private String ward;
	
	@Column(name = "location", length = 50,nullable = true)
	private String location;
	
	@Column(name = "gender", length = 10,nullable = true)
	private String gender;
	
    @Column(name = "location_latitude",nullable = true)
    private Float locationLatitude;

    @Column(name = "location_longitude",nullable = true)
    private Float location_longitude;

	@Column(name = "block_code", length = 50,nullable = true)
	private String blockCode;
	
	@Column(name = "block_number", length = 50,nullable = true)
	private String blockNumber;
	
	@Column(name = "record_status_id", nullable = false)
	private Long recordStatusId = 1L;
	
}
