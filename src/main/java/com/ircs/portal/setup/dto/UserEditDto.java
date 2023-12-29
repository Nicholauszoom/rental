package com.ircs.portal.setup.dto;

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
public class UserEditDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String userId;
	
	private String mobileNumber2;

	private String streetOrVillage;

	private String plotNumber;

	private String houseNumber;

	private String zipCode;

	private String postalAddress;

	private Long wardId;
	
	private String designation;
	
	private boolean enabled;
	
	private boolean locked;
	
	private Long pariamentMemberTypeId;
	
	private String parliamentConstituency;

}
