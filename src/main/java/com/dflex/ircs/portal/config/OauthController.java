package com.dflex.ircs.portal.config;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dflex.ircs.portal.util.Constants;
import com.dflex.ircs.portal.util.Response;
import com.dflex.ircs.portal.util.Utils;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
public class OauthController {
	
	@Autowired
	private Utils util;
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	private MessageSource messageSource;
	
	@Value("${spring.oauth2.server.uri}")
	private String oauthServer;
	
	String message = null;
	String status = null;
	Boolean isError = false;
	Locale currentLocale = LocaleContextHolder.getLocale();
	
	private SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

	/**
	 * Authorize Resource Owner and Client Details
	 * @param authentication
	 * @param request
	 * @param response
	 * @param OauthDetails
	 * @return String
	 */
	@PostMapping("/api/authorize")
    public ResponseEntity<?> authorize(HttpServletRequest request, HttpServletResponse response) {
		
		Authentication authentication = null;
		try {
			
			final String authorization = request.getHeader("Authorization")!=null?request.getHeader("Authorization"):request.getHeader("authorization");
			final String clientKey = request.getHeader("ClientKey")!=null?request.getHeader("clientKey"):request.getHeader("clientkey");
			final String clientId = request.getHeader("ClientId")!=null?request.getHeader("clientId"):request.getHeader("clientid");
			if (authorization != null && authorization.toLowerCase().startsWith("basic") && !util.isNullOrEmpty(clientKey)
					&& !util.isNullOrEmpty(clientId)) {
			    
			    String base64Credentials = authorization.substring("Basic".length()).trim();
			    byte[] credDecoded = Base64.decodeBase64(base64Credentials);
			    String credentials = new String(credDecoded, StandardCharsets.UTF_8);
			    String username = credentials.substring(0,credentials.indexOf(":"));
			    String password = credentials.substring(credentials.indexOf(":")+1);
			    
			    UsernamePasswordAuthenticationToken authToken = UsernamePasswordAuthenticationToken
						.unauthenticated(username,password);
				authentication = authenticationManager.authenticate(authToken);
				
				if(authentication != null) {
					
					SecurityContext context = SecurityContextHolder.getContext();
					context.setAuthentication(authentication);
					securityContextRepository.saveContext(context, request, response);
					HttpSession session = request.getSession(true);
					session.setAttribute("SPRING_SECURITY_CONTEXT", context);
					
					String clientStatus = "";
					String clientSecret = "";
					if(clientId.equals(Constants.CLIENT_ID_PORTAL)) {
						clientSecret = Constants.CLIENT_SECRET_PORTAL;
						clientStatus = validateClientDetails(clientKey,clientId,clientSecret);
					} else {
						clientSecret = Constants.CLIENT_SECRET_MOBILE;
						clientStatus = validateClientDetails(clientKey,clientId,clientSecret);
					}
					
					if(clientStatus.equals(Constants.DEFAULT_SUCCESS)) {
						
						Map<String,String> clientDetails = new HashMap<>();
						clientDetails.put("clientId", clientId);
						clientDetails.put("clientSecret", clientSecret);
						
						Map<String, String> codeDetails = getAuthorizationCode(session, clientDetails);
						if(codeDetails.get("status").equals("302") && !codeDetails.get("code").isBlank()) {
						
							String authorizationCode = codeDetails.get("code");
							String codeVerifier = codeDetails.get("codeVerifier");
							
							Map<String, String> tokenDetails = requestOauth2Token(clientDetails, authorizationCode, codeVerifier,session.getId());
							if(tokenDetails.get("status").equals("200") && !tokenDetails.get("token").isBlank()) {
								
								ObjectMapper objectMapper = new ObjectMapper();
								Token token = objectMapper.readValue(tokenDetails.get("token"),Token.class);
								
								message = messageSource.getMessage("message.1001",null, currentLocale);
								status = messageSource.getMessage("code.1001",null, currentLocale);
								isError  = false;
								Response res = new Response(String.valueOf(Calendar.getInstance().getTime()),status,isError,message,token,request.getRequestURI());
								return ResponseEntity.status(HttpStatus.OK).body(res);
								
							} else {
								message = messageSource.getMessage("message.1065",null, currentLocale);
								status = messageSource.getMessage("code.1065",null, currentLocale);
								isError  = true;
								Response res = new Response(String.valueOf(Calendar.getInstance().getTime()),status,isError,message,null,request.getRequestURI());
						        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
						        
							}
						} else {
							message = messageSource.getMessage("message.1065",null, currentLocale);
							status = messageSource.getMessage("code.1065",null, currentLocale);
							isError  = true;
							Response res = new Response(String.valueOf(Calendar.getInstance().getTime()),status,isError,message,null,request.getRequestURI());
					        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
						}
					} else {
						message = messageSource.getMessage("message.1064",null, currentLocale);
						status = messageSource.getMessage("code.1064",null, currentLocale);
						isError  = true;
						Response res = new Response(String.valueOf(Calendar.getInstance().getTime()),status,isError,message,null,request.getRequestURI());
				        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
					}
				} else {
					message = messageSource.getMessage("message.1064",null, currentLocale);
					status = messageSource.getMessage("code.1064",null, currentLocale);
					isError  = true;
					Response res = new Response(String.valueOf(Calendar.getInstance().getTime()),status,isError,message,null,request.getRequestURI());
			        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
				}
			} else {
				message = messageSource.getMessage("message.1064",null, currentLocale);
				status = messageSource.getMessage("code.1064",null, currentLocale);
				isError  = true;
				Response res = new Response(String.valueOf(Calendar.getInstance().getTime()),status,isError,message,null,request.getRequestURI());
		        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
			}
		} catch (BadCredentialsException ex) {
			ex.printStackTrace();
			message = messageSource.getMessage("message.1064",null, currentLocale);
			status = messageSource.getMessage("code.1064",null, currentLocale);
			isError  = true;
			Response res = new Response(String.valueOf(Calendar.getInstance().getTime()),status,isError,message,null,request.getRequestURI());
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
		
		} catch (DisabledException ex) {
			ex.printStackTrace();
			message = messageSource.getMessage("message.1064",null, currentLocale);
			status = messageSource.getMessage("code.1064",null, currentLocale);
			isError  = true;
			Response res = new Response(String.valueOf(Calendar.getInstance().getTime()),status,isError,message,null,request.getRequestURI());
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
		
		} catch (Exception ex) {
			ex.printStackTrace();
			message = messageSource.getMessage("message.1004",null, currentLocale);
			status = messageSource.getMessage("code.1004",null, currentLocale);
			isError  = true;
			Response res = new Response(String.valueOf(Calendar.getInstance().getTime()),status,isError,message,null,request.getRequestURI());
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
		}
		
    }
	
	/**
	 * Get Authorization Code
	 * @param session
	 * @param oauthDetails
	 * @return Map<String, String>
	 * @throws MalformedURLException
	 * @throws IOException
	 * @throws Exception
	 */
	private Map<String, String> getAuthorizationCode(HttpSession session,Map<String,String> clientDetails) throws MalformedURLException,IOException,Exception{
		
		String authorizationCode = null;
		
		String responseType = "code";
		String state = String.valueOf(ThreadLocalRandom.current().nextInt(1, 999999 + 1));
		String scope = "message.write";
		String redirectUrl = new URI(oauthServer+Constants.OAUTH2_AUTHORIZE_REDIRECT_URL) + "&continue";
		String codeVerifier = util.getCodeVerifier();
		String codeChallenge = util.getCodeChallenge(codeVerifier);
		String clientId = clientDetails.get("clientId");

		String authorizeUrl = oauthServer+Constants.OAUTH2_AUTHORIZE_URL + "?response_type=" + responseType + "" + "&client_id="
				+ clientId + "&state=" + state + "&scope=" + scope + "&redirect_uri=" + redirectUrl + ""
				+ "&code_challenge=" + codeChallenge + "&code_challenge_method=" + Constants.OAUTH2_CHALLENGE_METHOD;

		URL obj = new URL(authorizeUrl);
		HttpURLConnection connnection = (HttpURLConnection) obj.openConnection();

		// Request header
		connnection.setRequestMethod("GET");
		connnection.setRequestProperty("Cookie", "JSESSIONID=" + session.getId());

		connnection.setInstanceFollowRedirects(false);
		connnection.connect();
		int responseCode = connnection.getResponseCode();
		if(responseCode == 302) {
			
			String location = connnection.getHeaderField("Location");
			Map<String, List<String>> params = util.getUrlQueryParams(location);
			authorizationCode = params.getOrDefault("code", null).get(0);
			
		}
		
		Map<String, String> codeDetails = new HashMap<String, String>();
		codeDetails.put("code", authorizationCode);
		codeDetails.put("codeVerifier", codeVerifier);
		codeDetails.put("status", String.valueOf(responseCode));

		return codeDetails;
	}
	
	
	/**
	 * Get Outh2 Token
	 * @param oauthDetails
	 * @param clientId
	 * @param authorizationCode
	 * @param codeVerifier
	 * @param sessionId
	 * @return Map<String, String>
	 * @throws Exception
	 */
	private Map<String, String> requestOauth2Token(Map<String,String> clientDetails,String authorizationCode, String codeVerifier,
			String sessionId) throws Exception{

		Map<String,String> tokenDetails = new HashMap<>();

		String clientId = clientDetails.get("clientId");
		String clientSecret = clientDetails.get("clientSecret");;
		String grantType = "authorization_code";
			
		URL obj = new URL(oauthServer+Constants.OAUTH2_ACCESS_TOKEN_URL);
		HttpURLConnection connnection = (HttpURLConnection) obj.openConnection();

		// Request header
		String basicAuth = clientId + ":" + clientSecret;
		byte[] encodedAuth = Base64.encodeBase64(basicAuth.getBytes(StandardCharsets.UTF_8));
		String authHeaderValue = "Basic " + new String(encodedAuth);

		connnection.setRequestMethod("POST");
		connnection.setRequestProperty("Cookie", "JSESSIONID=" + sessionId);
		connnection.setRequestProperty("Authorization", authHeaderValue);

		String params = "client_id=" + clientId + "&redirect_uri=" + oauthServer+Constants.OAUTH2_AUTHORIZE_REDIRECT_URL
				+ "&grant_type="+grantType+"&code=" + authorizationCode + "&code_verifier=" + codeVerifier;
		
		// Send request
		connnection.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(connnection.getOutputStream());
		wr.writeBytes(params);
		wr.flush();
		wr.close();

		int responseCode = connnection.getResponseCode();

		System.out.println("token Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(connnection.getInputStream()));

		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		tokenDetails.put("token", response.toString());
		tokenDetails.put("status",String.valueOf(responseCode));
		return tokenDetails;

	}
	
	/**
	 * Authorize Resource Owner and Client Details
	 * @param authentication
	 * @param request
	 * @param response
	 * @param OauthDetails
	 * @return String
	 */
	@PostMapping("/api/token")
    public ResponseEntity<?> getAuthorizationToken(HttpServletRequest request, HttpServletResponse response) {
		
		Response res = null;
		try {
			
			String clientKey = request.getHeader("ClientKey")!=null?request.getHeader("ClientKey"):request.getHeader("clientkey");
			String clientId = request.getHeader("ClientId")!=null?request.getHeader("ClientId"):request.getHeader("clientid");
			if (!util.isNullOrEmpty(clientKey) && !util.isNullOrEmpty(clientId)) {
				
				String clientStatus = "";
				String clientSecret = "";
				if(clientId.equals(Constants.CLIENT_ID_PORTAL)) {
					clientSecret = Constants.CLIENT_SECRET_PORTAL;
					clientStatus = validateClientDetails(clientKey,clientId,clientSecret);
				} else {
					clientSecret = Constants.CLIENT_SECRET_MOBILE;
					clientStatus = validateClientDetails(clientKey,clientId,clientSecret);
				}
				
				if(clientStatus.equals(Constants.DEFAULT_SUCCESS)) {
					
					String grantType = "client_credentials";
					
					URL obj = new URL(oauthServer+Constants.OAUTH2_ACCESS_TOKEN_URL);
					HttpURLConnection connnection = (HttpURLConnection) obj.openConnection();

					// Request header
					String basicAuth = clientId + ":" + clientSecret;
					byte[] encodedAuth = Base64.encodeBase64(basicAuth.getBytes(StandardCharsets.UTF_8));
					String authHeaderValue = "Basic " + new String(encodedAuth);

					connnection.setRequestMethod("POST");
					connnection.setRequestProperty("Authorization", authHeaderValue);

					String params = "grant_type="+grantType;
					
					// Send request
					connnection.setDoOutput(true);
					DataOutputStream wr = new DataOutputStream(connnection.getOutputStream());
					wr.writeBytes(params);
					wr.flush();
					wr.close();

					int responseCode = connnection.getResponseCode();

					System.out.println("token Response Code : " + responseCode);

					BufferedReader in = new BufferedReader(new InputStreamReader(connnection.getInputStream()));

					String inputLine;
					StringBuffer tokenResponse = new StringBuffer();
					while ((inputLine = in.readLine()) != null) {
						tokenResponse.append(inputLine);
					}
					in.close();

					System.out.println("response.toString() : " + tokenResponse.toString());
					//tokenDetails.put("token", response.toString());
					if(responseCode == 200) {
						
						ObjectMapper objectMapper = new ObjectMapper();
						Token token = objectMapper.readValue(tokenResponse.toString(),Token.class);
						
						message = messageSource.getMessage("message.1001",null, currentLocale);
						status = messageSource.getMessage("code.1001",null, currentLocale);
						isError  = false;
						res = new Response(String.valueOf(Calendar.getInstance().getTime()),status,isError,message,token,request.getRequestURI());
						return ResponseEntity.status(HttpStatus.OK).body(res);
						
					} else {
						message = messageSource.getMessage("message.1065",null, currentLocale);
						status = messageSource.getMessage("code.1065",null, currentLocale);
						isError  = true;
						res = new Response(String.valueOf(Calendar.getInstance().getTime()),status,isError,message,null,request.getRequestURI());
						return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
					}
				} else {
					message = messageSource.getMessage("message.1064",null, currentLocale);
					status = messageSource.getMessage("code.1064",null, currentLocale);
					isError  = true;
					res = new Response(String.valueOf(Calendar.getInstance().getTime()),status,isError,message,null,request.getRequestURI());
					return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
				}
			} else {
				message = messageSource.getMessage("message.1005",null, currentLocale);
				status = messageSource.getMessage("code.1005",null, currentLocale);
				isError  = true;
				res = new Response(String.valueOf(Calendar.getInstance().getTime()),status,isError,message,null,request.getRequestURI());
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			message = messageSource.getMessage("message.1004",null, currentLocale);
			status = messageSource.getMessage("code.1004",null, currentLocale);
			isError  = true;
			res = new Response(String.valueOf(Calendar.getInstance().getTime()),status,isError,message,null,request.getRequestURI());
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
		}
		 		
    }
	
	/**
	 * Validate Authorization client details
	 * @param clientKey
	 * @return String
	 */
	private String validateClientDetails(String clientKey,String clientId,String clientSecret){
		
		String data = clientId+clientSecret;
		System.out.println("data*********"+data);
		String hashData= util.generateHexHMacHash(clientSecret, data);
		System.out.println("hashData*********"+hashData);
		if(hashData.equals(clientKey)) {
			return Constants.DEFAULT_SUCCESS;
		} else {
			return Constants.DEFAULT_FAILURE;
		}
	}
	
	@GetMapping({"/error",""})
	public ResponseEntity<Response>  requireFullAuthentication(HttpServletRequest request, HttpServletResponse response) {
		message = messageSource.getMessage("message.1004",null, currentLocale);
		status = messageSource.getMessage("code.1004",null, currentLocale);
		isError  = true;
		Response res = new Response(String.valueOf(Calendar.getInstance().getTime()),status,isError,message,null,request.getRequestURI());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(res);
	}
	
}


