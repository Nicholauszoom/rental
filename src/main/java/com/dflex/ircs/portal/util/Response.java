/**
 * 
 */
package com.dflex.ircs.portal.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<R> {
	
	private String timestamp;
	private String status;
	private Boolean error;
	private String code;
	private String message;
	private Object data;
	private String path;
	
}
