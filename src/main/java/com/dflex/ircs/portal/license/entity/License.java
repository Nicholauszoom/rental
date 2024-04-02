package com.dflex.ircs.portal.license.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.dflex.ircs.portal.application.entity.Applicant;
import com.dflex.ircs.portal.setup.entity.LicenseStatus;
import com.dflex.ircs.portal.setup.entity.LicenseType;
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
 * @author Augustino Mwageni
 * 
 * Entity Class for Database Table tab_license
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "tab_license")
public class License extends CommonEntity implements  Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "license_id_generator")
	@SequenceGenerator(name = "license_id_generator", sequenceName = "seq_license", initialValue=1, allocationSize = 1)
	@Column(name = "id", nullable = false, precision = 11)
    private Long id;
	
	@GeneratedValue
	@UuidGenerator
	@Column(name="license_uid",nullable = false)
	private UUID licenseUid;
	@PrePersist
    protected void onCreate() {
        setLicenseUid(java.util.UUID.randomUUID());
    }
	
	@Column(name = "detail_reference_id")
	private Long detailReferenceId;
	
	@Column(name = "detail_reference_table",length=30)
	private String detailReferenceTable;
	
	@Column(name = "parent_license_id",nullable=true)
	private Long parentLicenseId;
	
	@ManyToOne(fetch=FetchType.EAGER, optional=false)
	@JoinColumn(name="license_type_id",nullable=false)
	private LicenseType licenseType;
	
	@Column(name = "license_number",length=50,nullable =false)
	private String licenseNumber;
	
	@Column(name = "license_name",length=250,nullable =false)
	private String licenseName;
	
	@ManyToOne(fetch=FetchType.EAGER, optional=false)
	@JoinColumn(name="applicant_id",nullable=false)
	private Applicant applicant;
	
	@Column(name = "issued_date",nullable=true)
	private Date issuedDate;
	
	@Column(name = "commenced_date",nullable=true)
	private Date commencedDate;
	
	@Column(name = "tenure",nullable=true)
	private int  tenure;
	
	@Column(name = "expiry_date",nullable=true)
	private Date expiryDate;
	
	@ManyToOne(fetch=FetchType.EAGER, optional=false)
	@JoinColumn(name="license_status_id",nullable=false)
	private LicenseStatus licenseStatus;
	
	@Column(name = "has_renew_request",nullable=false)
	private Boolean hasRenewRequest = false;
	
	@Column(name = "has_transfer_request",nullable=false)
	private Boolean hasTransferRequest = false;
	
	@Column(name = "is_paid", nullable = false)
	private Boolean isPaid = false;

	@Column(name="previous_license_id", nullable = true)
	private Long previousLicenseId;
	
	@Column(name="record_status_id", nullable = true)
	private Long recordStatusId = 1L;
}
