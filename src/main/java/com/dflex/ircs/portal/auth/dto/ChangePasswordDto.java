package com.dflex.ircs.portal.auth.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
/**
 * 
 * @author Augustino Mwageni
 * 
 * Class for user password change details
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChangePasswordDto implements Serializable{

	private static final long serialVersionUID = 1L;

	@JsonProperty("userid")
	private String userId;
	
	@JsonProperty("oldpassword")
	private String oldPassword;
	
	@JsonProperty("newpassword")
	private String newPassword;
}
