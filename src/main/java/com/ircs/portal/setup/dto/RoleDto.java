package com.ircs.portal.setup.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Augustino Mwageni
 * 
 *  Data Transformation Object for Class role
 *
 */
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String roleName;
	
	private String roleDescription;
	
	private List<Long> permissions;

}
