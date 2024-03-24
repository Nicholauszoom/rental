package com.dflex.ircs.portal.data.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Augustino Mwageni
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AppProcessingDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String applicationUid;
	
	private String user;
	
	private String decision;
	
	private String remark;

}
