package com.dflex.ircs.portal.auth.dto;

import java.io.Serializable;

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
public class UserDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String userName;
	
	private String password;

	private String emailAddress;
	
	private String mobileNumber;

	private String postalAddress;
	
	private String designation;
	
	private String firstName;
	
	private String middleName;
	
	private String lastName;
	
	private String gender;
	
	private String nationalIdentity;

}
