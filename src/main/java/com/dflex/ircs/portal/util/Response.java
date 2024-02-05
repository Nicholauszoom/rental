/**
 * 
 */
package com.dflex.ircs.portal.util;

import com.dflex.ircs.portal.config.Token;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Response<R> {



//	private String timestamp;
//	private String status;
//	private Boolean error;
	private String code;
	private String message;
	private Object data;
	private String path;


	public Response(String s, String status, Boolean isError, String message, Object o, String requestURI) {
//		this.timestamp = s;
//		this.status = status;
//		this.error = isError;
		this.message = message;
		this.data = o;
		this.path = requestURI;
	}


	public Response() {

	}
}
