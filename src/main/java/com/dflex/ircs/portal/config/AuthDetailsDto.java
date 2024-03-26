package com.dflex.ircs.portal.config;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * @author Augustino Mwageni
 * 
 * Token Authentication user details
 *
 */
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthDetailsDto {
	
	private UUID userId;
	
	private String userName;
	
	private String email;
	
	private String phoneNumber;
	
	private String fullName;
	
	private String workStation;
}
