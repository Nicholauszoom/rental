package com.dflex.ircs.portal.auth.dto;

import java.io.Serializable;
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
public class RoleDetailsMinDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	private Long roleId;
	
	private String roleName;

}
