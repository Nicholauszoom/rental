package com.dflex.ircs.portal.util;
import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;;

/**
 * @author Augustino Mwageni
 * 
 * Data Transformation Object for Class Mail
 *
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MailDto {
	
    private String strMailTo;
    
    private String mailToName;
 
    private String mailCc;
 
    private String mailBcc;
 
    private String mailSubject;
 
    private String mailContent;
 
    private String mailType;
 
    private List < Object > attachments;
    
    private Map < String, Object > model;
 
}
