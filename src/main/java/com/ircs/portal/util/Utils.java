package com.ircs.portal.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Component;

import com.ircs.portal.config.AuthDetailsDto;

/**
 * 
 * @author Augustino Mwageni
 *
 * Application shared utilities
 *
 */
@Component
public class Utils {
	
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
}
