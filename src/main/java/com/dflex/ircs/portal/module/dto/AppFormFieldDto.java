package com.dflex.ircs.portal.module.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 * @author Augustino Mwageni
 *
 *         DTO class for database table AppFormField
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AppFormFieldDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private String appFormFieldUid;
	
	private String fieldType;
	
	private Integer valueMinimumSize;
	
	private Integer valueMaximumSize;
	
	private String valueDefault;
	
	private String dataType;

	private String formDisplayText;
	
	private String listDisplayText;
	
	private String dataFieldName;
	
	private String dataFieldKey;
	
	private Long dataSourceTypeId;
	
	private Long recordStatusId;
	
	private String appLookupTypeUid;
	
	private Long appFormId;
	
	private String displaySizeClass;
	
	private String validation;
	
	private Boolean showOnList;
	
	private Object fieldLookupData;
}
