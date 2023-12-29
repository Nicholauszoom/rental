package com.ircs.portal.setup.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author Augustino Mwageni
 * 
 * Data Transformation Object for Class Role
 *
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class RoleDetailsDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	private Long roleId;
	
	private String roleName;

	private Set<PermissionDetailsDto> permissionDetails = new HashSet<PermissionDetailsDto>();

}
