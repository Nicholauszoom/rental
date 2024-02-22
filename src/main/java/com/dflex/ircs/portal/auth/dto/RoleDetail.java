package com.dflex.ircs.portal.auth.dto;

import java.util.HashSet;
import java.util.Set;

import com.dflex.ircs.portal.auth.entity.Permission;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RoleDetail{
	
	private String RoleName;
	
	private Set<Permission> permissions = new HashSet<Permission>();

}
