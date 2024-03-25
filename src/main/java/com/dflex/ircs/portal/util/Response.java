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
public class Response  {
	
	private String timestamp;
	private String status;
	private Boolean error;
	private String message;
	private Object data;
	private String path;

	public Response(String message,  Object t, boolean error){
		this.message = message;
		this.error = error;
		this.data =  t;
	}

	public Response(String message,  boolean error){
		this.message = message;
		this.error = error;
	}
	
}
