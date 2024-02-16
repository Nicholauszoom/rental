package com.dflex.ircs.portal.auth.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author Augustino Mwageni
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class UserDetailsDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String userId;
	
	private String firstName;
	
	private String middleName;
	
	private String lastName;
	
	private String gender;

	private String userName;
	
	private String userCategory;
	
	private Long userCategoryId;
	
	private String emailAddress;
	
	private String mobileNumber;

	private String postalAddress;
	
	private String designation;

	private boolean isEmailConfirmed;

	private boolean isPhoneNumberConfirmed;
	
	private Integer loginAttemptCount;

	private Date lastFailedLogin;
	
	private Date lastLogin;
	
	private Date passwordExpiryDate;
	
	private boolean firstLogin;
	
	private boolean resetPassword;
	
	private boolean isEnabled;

	private boolean isAccountNonExpired;

	private boolean isCredentialsNonExpired;

	private boolean isAccountNonLocked;

	private Set<RoleDetailsDto> roleDetails;
	
	private String institutionUId;
	
	private String institutionCode;
	
	private String institutionName;

}
