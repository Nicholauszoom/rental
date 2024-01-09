package com.dflex.ircs.portal.setup.dto;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetailsMinDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String firstName;
	
	private String middleName;
	
	private String lastName;
	
	private String gender;

	private String userName;

	private String emailAddress;
	
	private String mobileNumber1;
	
	private Set<RoleDetailsMinDto> role;

	/**
	 * @param id
	 * @param firstName
	 * @param middleName
	 * @param lastName
	 * @param gender
	 * @param userName
	 * @param emailAddress
	 * @param mobileNumber1
	 */
	public UserDetailsMinDto(String id, String firstName, String middleName, String lastName, String gender,
			String userName, String emailAddress, String mobileNumber1) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.gender = gender;
		this.userName = userName;
		this.emailAddress = emailAddress;
		this.mobileNumber1 = mobileNumber1;
	}
	

}
