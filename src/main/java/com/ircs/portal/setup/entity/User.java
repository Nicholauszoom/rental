package com.ircs.portal.setup.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ircs.portal.util.CommonEntity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author Augustino Mwageni
 * 
 * Entity class for database table tab_user
 *
 */

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "tab_user")
@JsonIgnoreProperties(ignoreUnknown = true)
public class User extends CommonEntity implements Serializable {
	
	private static final long serialVersionUID = -7781624204093241554L;

	@Id
	@GeneratedValue
	@UuidGenerator
	private UUID id;
    
	@NotEmpty(message="First Name can not be empty and can not exceed 100 characters long")
	@Column(name="first_name",nullable = false,length = 100)
	private String firstName;

	@Size(message="Middle Name can not be empty and can not exceed 100 characters long")
	@Column(name="middle_name",nullable = true, length = 100)
	private String middleName;

	@NotEmpty(message="Last Name can not be empty and can not exceed 100 characters long")
	@Column(name="last_name",nullable = false,length = 100)
	private String lastName;

	@NotEmpty(message="Invalid username. Username cannot be empty.")
	@Column(name="user_name",nullable = false,length = 50, unique = true)
	private String userName;
	
	@Column(name = "gender",nullable = false,length = 6)
	private String gender;
	
	@Column(name = "designation",nullable = false,length = 50)
	private String designation;

	@Column(name="password",length = 1000)
	private String password;

	@Column(name="postal_address",length = 100)
	private String postalAddress;
	
	@Column(name="email_address",nullable = false, length = 100)
	private String emailAddress;
	
	@Column(name = "national_identity_number", nullable = true, length = 150)
	private String nationalIdentityNumber;

	@NotEmpty(message="Invalid user phone number.")
	@Column(name="mobile_number",length = 100)
	private String mobileNumber;
	
	@Column(name = "login_attempt_count", nullable = false)
	private Integer loginAttemptCount = 0;

	@Column(name = "last_failed_login", nullable = true)
	private Date lastFailedLogin;
	
	@Column(name = "last_lLogin", nullable = true)
	private Date lastLogin;

	@Column(name = "is_enabled", nullable = false)
	private Boolean isEnabled = true;

	@Column(name = "is_account_non_expired", nullable = false)
	private Boolean isAccountNonExpired = true;

	@Column(name = "is_credentials_non_expired", nullable = false)
	private Boolean isCredentialsNonExpired = true;

	@Column(name = "is_account_non_locked", nullable = false)
	private Boolean isAccountNonLocked = true;
	
	@Column(name = "is_first_login", nullable = false)
	private Boolean isFirstLogin = false;
	
	@Column(name = "is_reset_password", nullable = false)
	private Boolean isResetPassword = false;
	
	@Column(name = "password_expiry_date", nullable = true)
	private Date passwordExpiryDate;
	
	@Column(name = "email_confirmed", nullable = false)
	private Boolean emailConfirmed;

	@Column(name = "phone_number_confirmed", nullable = false)
	private Boolean phoneNumberConfirmed;
	
	@Column(name = "sms_confirmation_token_count", nullable = true)
	private Integer smsConfirmationTokenCount = 0;
	
	@Column(name = "email_confirmation_token_count", nullable = true)
	private Integer emailConfirmationTokenCount = 0;

	@Column(name = "record_status_id", nullable = false)
	private Long recordStatusId = 1L;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "service_department_id", nullable = false)
	private ServiceDepartment serviceDepartment;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "work_station_id", nullable = true)
	private WorkStation workStation;
	
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_category_id", nullable = false)
	private UserCategory userCategory;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "tab_user_role", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	private Set<Role> role = new HashSet<>();

}
