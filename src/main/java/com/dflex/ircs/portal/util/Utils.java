package com.dflex.ircs.portal.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import com.dflex.ircs.portal.config.AuthDetailsDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

/**
 * 
 * @author Augustino Mwageni
 *
 * Application shared utilities
 *
 */
@Component
public class Utils {
	
	@Autowired
	private MessageSource messageSource;
	
	Locale currentLocale = LocaleContextHolder.getLocale();
	
	private Long tokenSuffix = 0L;
	
	public Map<String, List<String>> getUrlQueryParams(String url) {
	    try {
	        Map<String, List<String>> params = new HashMap<String, List<String>>();
	        String[] urlParts = url.split("\\?");
	        if (urlParts.length > 1) {
	            String query = urlParts[1];
	            for (String param : query.split("&")) {
	                String[] pair = param.split("=");
	                String key = URLDecoder.decode(pair[0], "UTF-8");
	                String value = "";
	                if (pair.length > 1) {
	                    value = URLDecoder.decode(pair[1], "UTF-8");
	                }

	                List<String> values = params.get(key);
	                if (values == null) {
	                    values = new ArrayList<String>();
	                    params.put(key, values);
	                }
	                values.add(value);
	            }
	        }

	        return params;
	    } catch (UnsupportedEncodingException ex) {
	        throw new AssertionError(ex);
	    }
	}
	
	public String getCodeVerifier() {
		
		SecureRandom sr = new SecureRandom();
		byte[] code = new byte[32];
		sr.nextBytes(code);
		String verifier = Base64.encodeBase64URLSafeString(code);
		
		return verifier;
	}
	
	public String getCodeChallenge(String verifier) {
		
		String challenge = "";
		try {
			
			byte[] bytes = verifier.getBytes("US-ASCII");
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(bytes, 0, bytes.length);
			byte[] digest = md.digest();
			challenge = Base64.encodeBase64URLSafeString(digest);
			
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return challenge;
	}
	
	
	/**
	 * Generate string data hash
	 * @param hashingKey
	 * @param data
	 * @return String
	 */
	public String generateHexHMacHash(String hashingKey, String data) {

		String hash = null;
		try {

			Mac mac = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret = new SecretKeySpec(hashingKey.getBytes(), "HmacSHA256");
			mac.init(secret);
			byte[] enc = mac.doFinal(data.getBytes());
			
			hash = bytesToHex(enc);
			return hash;
			
		} catch (Exception e) {
			return hash;
		}
	}

	/**
	 * Convert a byte array into a hex string
	 * @param bytes
	 * @return
	 */
	public  String bytesToHex(byte[] bytes) {
		final char[] hexArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
				'9', 'a', 'b', 'c', 'd', 'e', 'f' };
		char[] hexChars = new char[bytes.length * 2];
		int v;
		for (int j = 0; j < bytes.length; j++) {
			v = bytes[j] & 0xFF;
			hexChars[j * 2] = hexArray[v >>> 4];
			hexChars[j * 2 + 1] = hexArray[v & 0x0F];
		}
		return new String(hexChars);
	}
	
	/**
	 * Generate random number with digits excluding zero
	 * @param length (1-9)
	 * @return String
	 */
	public String generateRandomNumber(Integer length) {
		
		List<Integer> numbers = new ArrayList<>();
	    for(int i = 1; i < 10; i++){
	        numbers.add(i);
	    }

	    Collections.shuffle(numbers);

	    String result = "";
	    for(int i = 0; i < length; i++){
	        result += numbers.get(i).toString();
	    }
		return result;
	}

	/**
	 * Check String is null,empty or blank
	 * @param inputString
	 * @return boolean
	 */
	public boolean isNullOrEmpty(String inputString) {
		boolean result = false;
		if (inputString == null || inputString.isEmpty() || inputString.isBlank()) {
			result = true;
		}
		return result;
	}
	
	/**
	 * Generate Random String
	 * @param length
	 * @param type
	 * @return String
	 */
	public String generateRandomString(Integer length,String type) {
		String SALTCHARS = "";
		
		if(type.equals("ALPHANUMERIC")){
			SALTCHARS = "AaBbCcDdEeFfGgHhJjKkLMmNnPpQqRrSsTtUuVvWwXxYyZz1234567890";
		}else if(type.equals("NUMERIC")) {
			SALTCHARS = "1234567890";
		}else {
			SALTCHARS = "AaBbCcDdEeFfGgHhJjKkLMmNnPpQqRrSsTtUuVvWwXxYyZz";
		}
        
		StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < length) { 
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
        
	}
	
	/**
	 * Get Token Authentication Details
	 * @param JwtAuthenticationToken
	 * @return AuthDetailsDto
	 */
	public AuthDetailsDto getTokenAuthenticationDetails(JwtAuthenticationToken tokenAuth) {
		
		AuthDetailsDto authDetails = new AuthDetailsDto();
        
		authDetails.setUserName(tokenAuth.getName());
		Map<String,Object> tokenAttributes = tokenAuth.getTokenAttributes();
		if(tokenAttributes.containsKey("uuid"))
			authDetails.setUserId(UUID.fromString(String.valueOf(tokenAttributes.get("uuid"))));
		if(tokenAttributes.containsKey("emal"))
			authDetails.setEmail(String.valueOf(tokenAttributes.get("emal")));
		if(tokenAttributes.containsKey("phon"))
			authDetails.setPhoneNumber(String.valueOf(tokenAttributes.get("phon")));
		if(tokenAttributes.containsKey("pers"))
			authDetails.setFullName(String.valueOf(tokenAttributes.get("pers")));
		
		return authDetails;
	}
	
	
	/**
	 * Convert Date to LocalDateTime
	 * 
	 * @param date
	 * @return LocalDateTime
	 */
	public LocalDateTime getLocalDateTime(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
	}
	
	/**
	 * Convert Date to LocalDate
	 * 
	 * @param date
	 * @return LocalDate
	 */
	public LocalDate getLocalDate(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}
	
	/**
	 * This method generate string token based on current time
	 * @return String
	 * @throws
	 */
	public String getToken(){

		String token = "";
		String currTokenSuffix = "";
		tokenSuffix ++;  
		if(tokenSuffix < 10000) {
			currTokenSuffix = String.format("%04d",tokenSuffix);
		}else {
			tokenSuffix = 0L;
			currTokenSuffix = String.format("%04d",tokenSuffix);
		}

		token = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		token = token + currTokenSuffix;
		return token;
	}
	
	
	/**
	 * Get string within a json string
	 * @param jsonString
	 * @param jsonNode
	 * @return String
	 */
	public String getJsonNodeValueFromJsonString(String jsonString, String jsonNode) {
		String nodeValue = "";
		if (!isNullOrEmpty(jsonString) && !isNullOrEmpty(jsonNode)) {

			try {
				JSONObject jsonObject = new JSONObject(jsonString);
				nodeValue = jsonObject.getString(jsonNode);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return nodeValue;
	}
	
	/**
	 * Check if file exists
	 * @param fullfilePath
	 * @return boolean
	 */
	public boolean isFileExist(String fullfilePath) {
		boolean result = false;
		if (!isNullOrEmpty(fullfilePath)) {
			File fis = new File(fullfilePath);
			if (fis.exists()) {
				result = true;
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param jaxbContext
	 * @param object
	 * @return
	 */
	public String generateXmlString(JAXBContext jaxbContext, Object object) {
		
		String xmlInStringFormat = "";
		JAXBContext jaxbContext1 = null;
		StringWriter xmlStringWriter = null;
		Marshaller jaxbMarshaller1 = null;

		try {
			jaxbContext1 = jaxbContext;
			xmlStringWriter = new StringWriter();
			jaxbMarshaller1 = jaxbContext1.createMarshaller();
			jaxbMarshaller1.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
			jaxbMarshaller1.marshal(object, xmlStringWriter);
			xmlInStringFormat = xmlStringWriter.toString();
		} catch (Exception e) {
			xmlInStringFormat = "";
		}

		return xmlInStringFormat;
	}
	
	/**
	 * Get xml string within a specific xml tag inclusively
	 * @param xmlBody
	 * @param xmlTag
	 * @return String
	 */
	public String getStringWithinXml(String xmlBody, String xmlTag) {
		String xmlString = "";
		if (xmlBody != null && !xmlBody.isEmpty() && xmlTag != null && !xmlTag.isEmpty()) {

			try {
				xmlString = xmlBody.substring(xmlBody.indexOf("<" + xmlTag + ">"),xmlBody.indexOf("</" + xmlTag + ">") + xmlTag.length() + 3);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return xmlString;
	}
	
	/**
	 * Get xml string within a specific xml tag exclusively
	 * @param xmlBody
	 * @param xmlTag
	 * @return String
	 */
	public String getStringWithinXmlTagExclusive(String xmlBody, String xmlTag) {
		String xmlString = "";
		if (xmlBody != null && !xmlBody.isEmpty() && xmlTag != null && !xmlTag.isEmpty()) {

			try {
				xmlString = xmlBody.substring(xmlBody.indexOf("<" + xmlTag + ">")+xmlTag.length()+2,
						xmlBody.indexOf("</" + xmlTag + ">") );
			} catch (Exception e) {
			}
		}
		return xmlString;
	}
	
	/**
	 * Get request Remote IP Address
	 * @param request
	 * @return String
	 */
	public String getRequestIP(HttpServletRequest request) {
        for (String header: Constants.IP_HEADERS) { 
            String value = request.getHeader(header);
            if (value == null || value.isEmpty()) {
                continue;
            }
            String[] parts = value.split("\\s*,\\s*");
            return parts[0];
        }
        return request.getRemoteAddr();
    }
	
	/**
	 * Validate Xml Schema against Xsd
	 * @param requestXsdSchema
	 * @param requestContent
	 * @return Map
	 */
	public Map<String, String> validateRequestXMLSchema(InputStream  requestXsdSchema, String requestContent) {
		
		Map<String,String> result = new ConcurrentHashMap<String,String>();
		String saxException = "";
		String code = "";
		String remark = "";
		String message = "";
		
		try {
      	  	
			SchemaFactory factory =
            SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new StreamSource(requestXsdSchema));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new StringReader(requestContent)));
            
            code = messageSource.getMessage("code.1001", null, currentLocale);
      	  	remark = messageSource.getMessage("message.1001", null, currentLocale);
      	  	message = remark;
      	  	
		} catch (IOException e){
	      	
			System.out.println("Exception: "+e.getMessage());
	    	code = messageSource.getMessage("code.1002", null, currentLocale);
	    	remark = messageSource.getMessage("message.1002", null, currentLocale);
	    	message = remark +","+e.getMessage();
	         
	    } catch(SAXException saxEx){

	    	String exSource = saxEx.getMessage().replaceAll("[.,\']","");
	    	exSource = exSource.substring(exSource.indexOf("for type")+8).trim();
	    	System.out.println("SAX Exception: "+saxEx.getMessage());
	        saxException = saxEx.getMessage();
	        message = saxException;
	        
	        switch(exSource) {
	        case "strrequestid" :
				code = messageSource.getMessage("code.1042", null, currentLocale);
				remark = messageSource.getMessage("message.1042", null, currentLocale);
				break;
	        case "strcallbackurl" :
				code = messageSource.getMessage("code.1017", null, currentLocale);
				remark = messageSource.getMessage("message.1017", null, currentLocale);
				break;
	        case "intinvoicetype" :
				code = messageSource.getMessage("code.1043", null, currentLocale);
				remark = messageSource.getMessage("message.1043", null, currentLocale);
	        case "intdtlcount" :
				code = messageSource.getMessage("code.1044", null, currentLocale);
				remark = messageSource.getMessage("message.1044", null, currentLocale);
				break;
	        case "strservicegroup" :
				code = messageSource.getMessage("code.1045", null, currentLocale);
				remark = messageSource.getMessage("message.1045", null, currentLocale);
				break;
	        case "strinvoicenumber" :
				code = messageSource.getMessage("code.1019", null, currentLocale);
				remark = messageSource.getMessage("message.1019", null, currentLocale);
				break;
	        case "strserviceinstitution" :
				code = messageSource.getMessage("code.1018", null, currentLocale);
				remark = messageSource.getMessage("message.1018", null, currentLocale);
				break;
	        case "strworkstation" : 
				code = messageSource.getMessage("code.1020", null, currentLocale);
				remark = messageSource.getMessage("message.1020", null, currentLocale);
				break;
	        case "strinvoicedescription" :
				code = messageSource.getMessage("code.1046", null, currentLocale);
				remark = messageSource.getMessage("message.1046", null, currentLocale);
				break;
	        case "strcustomerid" :
				code = messageSource.getMessage("code.1027", null, currentLocale);
				remark = messageSource.getMessage("message.1027", null, currentLocale);
				break;
	        case "intcustomeridtype" :
				code = messageSource.getMessage("code.1026", null, currentLocale);
				remark = messageSource.getMessage("message.1026", null, currentLocale);
				break;
	        case "strcustomeraccount" :
				code = messageSource.getMessage("code.1047", null, currentLocale);
				remark = messageSource.getMessage("message.1047", null, currentLocale);
				break;
	        case "strcustomername" :
				code = messageSource.getMessage("code.1048", null, currentLocale);
				remark = messageSource.getMessage("message.1048", null, currentLocale);
				break;
	        case "strcustomerphonenumber" :
				code = messageSource.getMessage("code.1049", null, currentLocale);
				remark = messageSource.getMessage("message.1049", null, currentLocale);
				break;
	        case "strcustomeremail" :
				code = messageSource.getMessage("code.1050", null, currentLocale);
				remark = messageSource.getMessage("message.1050", null, currentLocale);
				break;
	        case "datetimeissue" :
				code = messageSource.getMessage("code.1021", null, currentLocale);
				remark = messageSource.getMessage("message.1021", null, currentLocale);
				break;
	        case "datetimeexpiry" :
				code = messageSource.getMessage("code.1022", null, currentLocale);
				remark = messageSource.getMessage("message.1022", null, currentLocale);
				break;
	        case "strissuedby" :
				code = messageSource.getMessage("code.1051", null, currentLocale);
				remark = messageSource.getMessage("message.1051", null, currentLocale);
				break;
	        case "strapprovedby" :
				code = messageSource.getMessage("code.1052", null, currentLocale);
				remark = messageSource.getMessage("message.1052", null, currentLocale);
				break;
	        case "decinvoiceamount" :
				code = messageSource.getMessage("code.1025", null, currentLocale);
				remark = messageSource.getMessage("message.1025", null, currentLocale);
				break;
	        case "intpaymentoption" :
				code = messageSource.getMessage("code.1062", null, currentLocale);
				remark = messageSource.getMessage("message.1062", null, currentLocale);
				break;
	        case "strcurrency" :
				code = messageSource.getMessage("code.1055", null, currentLocale);
				remark = messageSource.getMessage("message.1055", null, currentLocale);
				break;
	        case "decexchangerate" :
				code = messageSource.getMessage("code.1056", null, currentLocale);
				remark = messageSource.getMessage("message.1056", null, currentLocale);
				break;
	        case "intinvoiceplan" :
				code = messageSource.getMessage("code.1057", null, currentLocale);
				remark = messageSource.getMessage("message.1057", null, currentLocale);
				break;
	        case "strservicedepartment" :
				code = messageSource.getMessage("code.1058", null, currentLocale);
				remark = messageSource.getMessage("message.1058", null, currentLocale);
				break;
	        case "strservicetype" :
				code = messageSource.getMessage("code.1059", null, currentLocale);
				remark = messageSource.getMessage("message.1059", null, currentLocale);
				break;
	        case "strserviceref" :
				code = messageSource.getMessage("code.1060", null, currentLocale);
				remark = messageSource.getMessage("message.1060", null, currentLocale);
				break;
	        case "desserviceamount" :
				code = messageSource.getMessage("code.1061", null, currentLocale);
				remark = messageSource.getMessage("message.1061", null, currentLocale);
				break;
	        case "intpaymentpriority" :
				code = messageSource.getMessage("code.1063", null, currentLocale);
				remark = messageSource.getMessage("message.1063", null, currentLocale);
				break;
	        case "strhash" :
				code = messageSource.getMessage("code.1016", null, currentLocale);
				remark = messageSource.getMessage("message.1016", null, currentLocale);
				break;
			default:
				code = messageSource.getMessage("code.1005", null, currentLocale);
				remark = messageSource.getMessage("message.1005", null, currentLocale);
			}
	    }
		
		result.put("code", code);
        result.put("remark", remark);
        result.put("message", message);
        
		return result;
	}

	public String getTimeStamp() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentDate = new Date();
		return dateFormat.format(currentDate);
	}


	public void copyNonNullProperties(Object source, Object destination){
		BeanUtils.copyProperties(source, destination,
				getNullPropertyNames(source));
	}


	private static String[] getNullPropertyNames (Object source) {
		final BeanWrapper src = new BeanWrapperImpl(source);
		java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

		Set emptyNames = new HashSet();
		for(java.beans.PropertyDescriptor pd : pds) {
			//check if value of this property is null then add it to the collection
			Object srcValue = src.getPropertyValue(pd.getName());
			if (srcValue == null) emptyNames.add(pd.getName());
		}
		String[] result = new String[emptyNames.size()];
		return (String[]) emptyNames.toArray(result);
	}
}
