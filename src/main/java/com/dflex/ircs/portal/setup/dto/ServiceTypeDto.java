package com.dflex.ircs.portal.setup.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ServiceTypeDto implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private String serviceTypeUid;
	
	private String serviceTypeCode;

	private String serviceTypeName;

	private Long parent_service_type_id;
	
	private Integer serviceTypeLevel;
	
	private Long recordStatusId;
}