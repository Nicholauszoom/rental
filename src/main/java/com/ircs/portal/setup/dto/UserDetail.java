package com.ircs.portal.setup.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDetail implements Serializable   {

	private static final long serialVersionUID = 8172941003743673313L;

	private Long systemUserId;

	private String firstName;

	private String middleName;

	private String lastName;

	private String userName;

	private String password;

	private String address;
	
	private String email;

	private String phone;
	
	private Long statusId;
	
	private String statusDescription;

	private Long organizationId;
	
	private String organizationName;

	private Long personnelTitleId;
	
	private String personnelTitle;

	private Long roleId;

	private String profilePicture;

	private Long recordCreatedBy;

	private Date recordCreatedDate;
	
	private boolean enabled;

	private boolean nonExpired;

	private boolean nonLocked;
	
	private boolean credentialsNonExpired;

	private boolean resetPassword;
	
	private Date passwordExpiryDate;
	
	private Set<RoleDetail> userRoleDetails = new HashSet<RoleDetail>();

}
