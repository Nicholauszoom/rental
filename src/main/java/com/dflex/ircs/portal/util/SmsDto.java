package com.dflex.ircs.portal.util;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author Augustino Mwageni
 * 
 * SMS Data Transformation Object 
 *
 */

@Data
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class SmsDto {
	
    private String receiver;
    
    private String smsContent;

	private String mobileServiceId;
 
	private String secretKey;
	
	private String userName;

	/**
	 * @param receiver
	 * @param smsContent
	 */
	public SmsDto(String receiver, String smsContent) {
		super();
		this.receiver = receiver;
		this.smsContent = smsContent;
	}
	
}
