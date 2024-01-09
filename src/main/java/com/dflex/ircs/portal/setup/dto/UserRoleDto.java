package com.dflex.ircs.portal.setup.dto;

import java.util.List;

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
public class UserRoleDto {
	
	private String id;
	
	private List<Long> roleId;

}
