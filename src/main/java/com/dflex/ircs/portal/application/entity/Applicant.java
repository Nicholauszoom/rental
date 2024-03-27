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
	
	@JoinColumn(name = "identity_type_name", nullable = false)
	private String identityTypeName;
	
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
	
	@Column(name = "gender_id",nullable = true)
	private Long genderId;
	
	@Column(name = "gender_name", length = 10,nullable = true)
	private String genderName;
	
    @Column(name = "location_latitude",nullable = true)
    private Float locationLatitude;

    @Column(name = "location_longitude",nullable = true)
    private Float locationLongitude;

	@Column(name = "block_code", length = 50,nullable = true)
	private String blockCode;
	
	@Column(name = "block_number", length = 50,nullable = true)
	private String blockNumber;
	
	@Column(name = "record_status_id", nullable = false)
	private Long recordStatusId = 1L;

	/**
	 * @param createdBy
	 * @param createdByUserName
	 * @param applicantAccount
	 * @param applicantName
	 * @param identityNumber
	 * @param identityTypeId
	 * @param identityTypeName
	 * @param nationality
	 * @param telephoneNumber
	 * @param mobileNumber
	 * @param whatsappNumber
	 * @param emailAddress
	 * @param postalAddress
	 * @param plotNumber
	 * @param street
	 * @param ward
	 * @param location
	 * @param genderId
	 * @param genderName
	 * @param locationLatitude
	 * @param locationLongitude
	 * @param blockCode
	 * @param blockNumber
	 */
	public Applicant(UUID createdBy, String createdByUserName, String applicantAccount, String applicantName,
			String identityNumber, Long identityTypeId, String identityTypeName, String nationality,
			String telephoneNumber, String mobileNumber, String whatsappNumber, String emailAddress,
			String postalAddress, String plotNumber, String street, String ward, String location, Long genderId,
			String genderName, Float locationLatitude, Float locationLongitude, String blockCode, String blockNumber) {
		super(createdBy, createdByUserName);
		this.applicantAccount = applicantAccount;
		this.applicantName = applicantName;
		this.identityNumber = identityNumber;
		this.identityTypeId = identityTypeId;
		this.identityTypeName = identityTypeName;
		this.nationality = nationality;
		this.telephoneNumber = telephoneNumber;
		this.mobileNumber = mobileNumber;
		this.whatsappNumber = whatsappNumber;
		this.emailAddress = emailAddress;
		this.postalAddress = postalAddress;
		this.plotNumber = plotNumber;
		this.street = street;
		this.ward = ward;
		this.location = location;
		this.genderId = genderId;
		this.genderName = genderName;
		this.locationLatitude = locationLatitude;
		this.locationLongitude = locationLongitude;
		this.blockCode = blockCode;
		this.blockNumber = blockNumber;
	}
	
	
}
