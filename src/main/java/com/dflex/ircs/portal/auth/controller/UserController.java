package com.dflex.ircs.portal.auth.controller;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dflex.ircs.portal.auth.dto.ChangePasswordDto;
import com.dflex.ircs.portal.auth.dto.PermissionDetailsDto;
import com.dflex.ircs.portal.auth.dto.RoleDetailsDto;
import com.dflex.ircs.portal.auth.dto.RoleDetailsMinDto;
import com.dflex.ircs.portal.auth.dto.UserDetailsDto;
import com.dflex.ircs.portal.auth.dto.UserDetailsMinDto;
import com.dflex.ircs.portal.auth.dto.UserDto;
import com.dflex.ircs.portal.auth.dto.UserEditDto;
import com.dflex.ircs.portal.auth.dto.UserRoleDto;
import com.dflex.ircs.portal.auth.entity.Permission;
import com.dflex.ircs.portal.auth.entity.Role;
import com.dflex.ircs.portal.auth.entity.User;
import com.dflex.ircs.portal.auth.entity.UserCategory;
import com.dflex.ircs.portal.auth.service.RoleService;
import com.dflex.ircs.portal.auth.service.UserCategoryService;
import com.dflex.ircs.portal.auth.service.UserService;
import com.dflex.ircs.portal.config.AuthDetailsDto;
import com.dflex.ircs.portal.setup.service.ServiceDepartmentService;
import com.dflex.ircs.portal.util.Constants;
import com.dflex.ircs.portal.util.MailDto;
import com.dflex.ircs.portal.util.Response;
import com.dflex.ircs.portal.util.SmsDto;
import com.dflex.ircs.portal.util.Utils;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 
 * @author Augustino Mwageni
 * 
 * User Management Api Controller
 *
 */

@RestController
@RequestMapping(value="/api/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ServiceDepartmentService departmentService;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private UserCategoryService userCategoryService;
	
	@Autowired
	private Utils util;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private MessageSource messageSource;
	
	@Value("${app.user.password.life-span}")
	private Integer passwordLife;
	
	@Value("${app.user.one-time-password.life-span}")
	private Integer oneTimePasswordLife;
	
	@Value("${app.phonenumber.verification.token.life}")
	private Integer phoneNumberVerificationTokenLife;
	
	@Value("${app.email.verification.link.life}")
	private Integer emailVerificationLinkLife;
	
	@Value("${app.email.verification.link.prefix}")
	private String emailVerificationPrefix;
	
	@Value("${app.password.reset.link.prefix}")
	private String passwordResetLinkPrefix;
	
	@Value("${app.default.login.url}")
	private String loginUrl;
	
	private Map<String,String> inMemoryUserVerificationTokens = new HashMap<>();
	
	String message = null;
	String status = null;
	Boolean isError = false;
	Locale currentLocale = LocaleContextHolder.getLocale();
	
	/**
	 * Get All users
	 * @param request
	 * @return ResponseEntity
	 */
	//@PreAuthorize("hasAuthority('LIST_USER')")
	@GetMapping("/min")
	public ResponseEntity<?> getAllUsersMinDetails(HttpServletRequest request){

		List<UserDetailsMinDto> userList = new ArrayList<>();
		try {
			
			List<User> users = userService.findAll();
			if(users != null && !users.isEmpty()) {
				
				for(User user:users) {
					
					Set<RoleDetailsMinDto> roles = new HashSet<>();
					if (user.getRole() != null && !user.getRole().isEmpty()) {

						for (Role role : user.getRole()) {
							roles.add(new RoleDetailsMinDto(role.getId(),role.getRoleName()));
						}
					}
					userList.add(new UserDetailsMinDto(String.valueOf(user.getId()),user.getFirstName(),user.getMiddleName(),user.getLastName(),user.getGender(),
							user.getUserName(),user.getEmailAddress(),user.getMobileNumber(),roles));
				}
				message = messageSource.getMessage("message.1001",null, currentLocale);
				status = messageSource.getMessage("code.1001",null, currentLocale);
				isError  = false;
			} else {
				message = messageSource.getMessage("message.1009",null, currentLocale);
				status = messageSource.getMessage("code.1009",null, currentLocale);
				isError  = true;
			}
		} catch(Exception ex) {
			message = messageSource.getMessage("message.1004",null, currentLocale);
			status = messageSource.getMessage("code.1004",null, currentLocale);
			isError  = true;
		}
		Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,isError,message,userList,request.getRequestURI());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
	/**
	 * Get All users
	 * @param request
	 * @return ResponseEntity
	 */
	//@PreAuthorize("hasAuthority('LIST_ROLE_USER')")
	@GetMapping("/min/role/{roleid}")
	public ResponseEntity<?> getAllUsersMinDetailsByRoleId(@PathVariable("roleid") Long roleId,HttpServletRequest request){

		List<UserDetailsMinDto> userList = new ArrayList<>();
		try {
			
			List<User> users = userService.findAllByRoleId(roleId);
			if(users != null && !users.isEmpty()) {
				
				for(User user:users) {
					
					Set<RoleDetailsMinDto> roles = new HashSet<>();
					if (user.getRole() != null && !user.getRole().isEmpty()) {

						for (Role role : user.getRole()) {
							roles.add(new RoleDetailsMinDto(role.getId(),role.getRoleName()));
						}
					}
					userList.add(new UserDetailsMinDto(String.valueOf(user.getId()),user.getFirstName(),user.getMiddleName(),user.getLastName(),user.getGender(),
							user.getUserName(),user.getEmailAddress(),user.getMobileNumber(),roles));
					
				}
				message = messageSource.getMessage("message.1001",null, currentLocale);
				status = messageSource.getMessage("code.1001",null, currentLocale);
				isError  = false;
			} else {
				message = messageSource.getMessage("message.1009",null, currentLocale);
				status = messageSource.getMessage("code.1009",null, currentLocale);
				isError  = true;
			}
		} catch(Exception ex) {
			message = messageSource.getMessage("message.1004",null, currentLocale);
			status = messageSource.getMessage("code.1004",null, currentLocale);
			isError  = true;
		}
		Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,isError,message,userList,request.getRequestURI());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	/**
	 * Get All Institution users with specific roles
	 * @param request
	 * @return ResponseEntity
	 */
	//@PreAuthorize("hasAuthority('LIST_ROLE_USER')")
	@PostMapping("/min-role-users")
	public ResponseEntity<?> getAllUsersMinDetailsByRoleIdList(@PathVariable("institutioncode") String institutionCode,@RequestBody Long roleId,
			HttpServletRequest request){

		List<UserDetailsMinDto> userList = new ArrayList<>();
		try {
			
			List<User> users = userService.findAllByRoleId(roleId);
			if(users != null && !users.isEmpty()) {
				
				for(User user:users) {
					
					userList.add(new UserDetailsMinDto(String.valueOf(user.getId()),user.getFirstName(),user.getMiddleName(),user.getLastName(),user.getGender(),
							user.getUserName(),user.getEmailAddress(),user.getMobileNumber()));
					
				}
				message = messageSource.getMessage("message.1001",null, currentLocale);
				status = messageSource.getMessage("code.1001",null, currentLocale);
				isError  = false;
			} else {
				message = messageSource.getMessage("message.1009",null, currentLocale);
				status = messageSource.getMessage("code.1009",null, currentLocale);
				isError  = true;
			}
		} catch(Exception ex) {
			message = messageSource.getMessage("message.1004",null, currentLocale);
			status = messageSource.getMessage("code.1004",null, currentLocale);
			isError  = true;
		}
		Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,isError,message,userList,request.getRequestURI());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
	//@PreAuthorize("hasAuthority('VIEW_FULL_USERINFO')")
	@GetMapping("/userinfo")
	public ResponseEntity<?> getUserDetails(Authentication authentication,HttpServletRequest request){
		
		String userName = authentication.getName();
		Optional<User> user = userService.findByUserName(userName);
		UserDetailsDto userDetails = null;
		
		if(user.isPresent()) {
			
			userDetails = new UserDetailsDto();
			
			userDetails.setUserId(user.get().getId().toString());
			userDetails.setFirstName(user.get().getFirstName());
			userDetails.setMiddleName(user.get().getMiddleName());
			userDetails.setLastName(user.get().getLastName());
			userDetails.setGender(user.get().getGender()); 
			userDetails.setUserName(userName);
			userDetails.setUserCategory(user.get().getUserCategory().getUserCategoryName());
			userDetails.setUserCategoryId(user.get().getUserCategory().getId());
			userDetails.setEmailAddress(user.get().getEmailAddress());
			userDetails.setMobileNumber(user.get().getMobileNumber());
			userDetails.setDesignation(user.get().getDesignation());
			userDetails.setEmailConfirmed(user.get().getEmailConfirmed()==null?false:user.get().getEmailConfirmed());
			userDetails.setPhoneNumberConfirmed(user.get().getPhoneNumberConfirmed()==null?false:user.get().getPhoneNumberConfirmed());
			userDetails.setLoginAttemptCount(user.get().getLoginAttemptCount());
			userDetails.setLastFailedLogin(user.get().getLastFailedLogin());
			userDetails.setLastLogin(user.get().getLastLogin());
			userDetails.setPasswordExpiryDate(user.get().getPasswordExpiryDate());
			userDetails.setFirstLogin(user.get().getIsFirstLogin()==null?false:user.get().getIsFirstLogin());
			userDetails.setResetPassword(user.get().getIsResetPassword()==null?false:user.get().getIsResetPassword());
			userDetails.setEnabled(user.get().getIsEnabled()==null?false:user.get().getIsEnabled());
			userDetails.setAccountNonExpired(user.get().getIsAccountNonExpired()==null?false:user.get().getIsAccountNonExpired());
			userDetails.setCredentialsNonExpired(user.get().getIsCredentialsNonExpired()==null?false:user.get().getIsCredentialsNonExpired());
			userDetails.setAccountNonLocked(user.get().getIsAccountNonLocked()==null?false:user.get().getIsAccountNonLocked());
			
			if (user.get().getRole() != null && !user.get().getRole().isEmpty()) {

				Set<RoleDetailsDto> userRoles = new HashSet<RoleDetailsDto>();
				for (Role role : user.get().getRole()) {

					RoleDetailsDto roleDetails = null;
					if (role.getPermission() != null && !role.getPermission().isEmpty()) {

						Set<PermissionDetailsDto> permissionDetails = new HashSet<PermissionDetailsDto>();
						PermissionDetailsDto permissionDetail = null;
						for (Permission permission : role.getPermission()) {
							permissionDetail = new PermissionDetailsDto(permission.getPermissionName());
							permissionDetails.add(permissionDetail);
						}

						roleDetails = new RoleDetailsDto(role.getId(), role.getRoleName(), permissionDetails);
						userRoles.add(roleDetails);
					}
				}
				userDetails.setRoleDetails(userRoles);
			}

		}
		
		if (userDetails != null) {
			
			message = messageSource.getMessage("message.1001",null, currentLocale);
			status = messageSource.getMessage("code.1001",null, currentLocale);
			isError  = false;
			
		} else {
			message = messageSource.getMessage("message.1009",null, currentLocale);
			status = messageSource.getMessage("code.1009",null, currentLocale);
			isError  = true;
		}
		Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,isError,message,userDetails,request.getRequestURI());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
	//@PreAuthorize("hasAuthority('VIEW_FULL_USERINFO')")
	@GetMapping("/userinfo/id/{id}")
	public ResponseEntity<?> getUserDetailsById(@PathVariable("id") String userId, HttpServletRequest request) {

		Optional<User> user = userService.findById(UUID.fromString(userId));
		UserDetailsDto userDetails = null;

		if (user.isPresent()) {

			userDetails = new UserDetailsDto();

			userDetails.setUserId(user.get().getId().toString());
			userDetails.setFirstName(user.get().getFirstName());
			userDetails.setMiddleName(user.get().getMiddleName());
			userDetails.setLastName(user.get().getLastName());
			userDetails.setGender(user.get().getGender());
			userDetails.setUserName(user.get().getUserName());
			userDetails.setUserCategory(user.get().getUserCategory().getUserCategoryName());
			userDetails.setUserCategoryId(user.get().getUserCategory().getId());
			userDetails.setEmailAddress(user.get().getEmailAddress());
			userDetails.setMobileNumber(user.get().getMobileNumber());
			userDetails.setDesignation(user.get().getDesignation());
			userDetails
					.setEmailConfirmed(user.get().getEmailConfirmed() == null ? false : user.get().getEmailConfirmed());
			userDetails.setPhoneNumberConfirmed(
					user.get().getPhoneNumberConfirmed() == null ? false : user.get().getPhoneNumberConfirmed());
			userDetails.setLoginAttemptCount(user.get().getLoginAttemptCount());
			userDetails.setLastFailedLogin(user.get().getLastFailedLogin());
			userDetails.setLastLogin(user.get().getLastLogin());
			userDetails.setPasswordExpiryDate(user.get().getPasswordExpiryDate());
			userDetails.setFirstLogin(user.get().getIsFirstLogin() == null ? false : user.get().getIsFirstLogin());
			userDetails.setResetPassword(
					user.get().getIsResetPassword() == null ? false : user.get().getIsResetPassword());
			userDetails.setEnabled(user.get().getIsEnabled() == null ? false : user.get().getIsEnabled());
			userDetails.setAccountNonExpired(
					user.get().getIsAccountNonExpired() == null ? false : user.get().getIsAccountNonExpired());
			userDetails.setCredentialsNonExpired(
					user.get().getIsCredentialsNonExpired() == null ? false : user.get().getIsCredentialsNonExpired());
			userDetails.setAccountNonLocked(
					user.get().getIsAccountNonLocked() == null ? false : user.get().getIsAccountNonLocked());

			if (user.get().getRole() != null && !user.get().getRole().isEmpty()) {

				Set<RoleDetailsDto> userRoles = new HashSet<RoleDetailsDto>();
				for (Role role : user.get().getRole()) {

					RoleDetailsDto roleDetails = null;
					if (role.getPermission() != null && !role.getPermission().isEmpty()) {

						Set<PermissionDetailsDto> permissionDetails = new HashSet<PermissionDetailsDto>();
						PermissionDetailsDto permissionDetail = null;
						for (Permission permission : role.getPermission()) {
							permissionDetail = new PermissionDetailsDto(permission.getPermissionName());
							permissionDetails.add(permissionDetail);
						}

						roleDetails = new RoleDetailsDto(role.getId(), role.getRoleName(), permissionDetails);
						userRoles.add(roleDetails);
					}
				}
				userDetails.setRoleDetails(userRoles);
			}

		}

		if (userDetails != null) {

			message = messageSource.getMessage("message.1001", null, currentLocale);
			status = messageSource.getMessage("code.1001", null, currentLocale);
			isError = false;

		} else {
			message = messageSource.getMessage("message.1009", null, currentLocale);
			status = messageSource.getMessage("code.1009", null, currentLocale);
			isError = true;
		}
		Response response = new Response(String.valueOf(Calendar.getInstance().getTime()), status, isError, message,
				userDetails, request.getRequestURI());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	/**
	 * Create User
	 * @param userDto
	 * @param request
	 * @return ResponseEntity
	 */
	//@PreAuthorize("hasAuthority('CREATE_USER')")
	@PostMapping("/create")
	public  ResponseEntity<?> createUser(@RequestBody UserDto userDto,HttpServletRequest request){
		
		UUID createdUserId  = null;
		boolean phoneNumberConfirmed = false;
		boolean resetPassword = false;
		boolean firstLogin = false;
		boolean enabled = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		boolean accountNonExpired = true;
		boolean emailConfirmed = false;
		
		//Password
		byte[] credDecoded = Base64.decodeBase64(userDto.getPassword());
	    String plainPassword = new String(credDecoded, StandardCharsets.UTF_8);
	    String encryptedPassword = passwordEncoder.encode(plainPassword);
		
		//Password Expiry Date
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, passwordLife);
		Date passwordExpiryDate = calendar.getTime();
		
		try {	
			
			List<User> oldUsersByName = userService.getByUserName(userDto.getUserName().trim().toUpperCase());
			if((oldUsersByName == null || oldUsersByName.isEmpty()) || (oldUsersByName != null && !oldUsersByName.isEmpty() 
					&& (!oldUsersByName.get(0).getEmailConfirmed() || !oldUsersByName.get(0).getPhoneNumberConfirmed()))) {
				
				List<User> oldUsersByEmailAndPhone = userService.getByEmailAddressOrMobileNumberAndNotUserName(userDto.getEmailAddress().trim().toUpperCase(),
						userDto.getMobileNumber(),userDto.getUserName().trim().toUpperCase());
				if(oldUsersByEmailAndPhone == null || oldUsersByEmailAndPhone.isEmpty()) {
					
					User user = new User();
					if(oldUsersByName != null && !oldUsersByName.isEmpty() 
							&& (!oldUsersByName.get(0).getEmailConfirmed() || !oldUsersByName.get(0).getPhoneNumberConfirmed())){
						user = oldUsersByName.get(0);
					}
					
					Optional<UserCategory> userCategory = userCategoryService.findById(Constants.USER_CATEGORY_CUSTOMER);
					
					user.setCreatedBy(Constants.DEFAULT_SYS_USERID);
					user.setCreatedByUserName(Constants.DEFAULT_SYS_USERNAME);
					user.setCreatedDate(new Date());
					user.setEmailConfirmed(emailConfirmed);
					user.setIsAccountNonExpired(accountNonExpired);
					user.setIsAccountNonLocked(accountNonLocked);
					user.setIsCredentialsNonExpired(credentialsNonExpired);
					user.setIsEnabled(enabled);
					user.setIsFirstLogin(firstLogin);
					user.setIsResetPassword(resetPassword);
					user.setPhoneNumberConfirmed(phoneNumberConfirmed);
					user.setPasswordExpiryDate(passwordExpiryDate);
					Set<Role> roles = new HashSet<>();
					Optional<Role> role = roleService.findById(Constants.DEFAULT_CUSTOMER_USER_ROLE);
					roles.add(role.get());
					user.setRole(roles);
					
					user.setDesignation(userDto.getDesignation());
					user.setGender(userDto.getGender());
					
					user.setFirstName(userDto.getFirstName());
					user.setLastName(userDto.getLastName());
					user.setMiddleName(userDto.getMiddleName());
					user.setUserCategory(userCategory.get());
					user.setNationalIdentityNumber(userDto.getNationalIdentity());
					
					user.setEmailAddress(userDto.getEmailAddress());
					user.setMobileNumber(userDto.getMobileNumber());
					user.setUserName(userDto.getMobileNumber());
					user.setPassword(encryptedPassword);
					user.setPostalAddress(userDto.getPostalAddress());
					
					User newUser = userService.saveUser(user);
					createdUserId = newUser.getId();
					
					if(createdUserId != null) {
						
						message = messageSource.getMessage("general.create.success", new Object[]{"User "}, currentLocale);
						status = messageSource.getMessage("code.1001",null, currentLocale);
						isError  = false;
						
						//Email Verification
						requestEmailVerification(newUser);
						
					} else {
						message = messageSource.getMessage("general.create.failure", new Object[]{"User "}, currentLocale);
						status = messageSource.getMessage("code.1002",null, currentLocale);
						isError  = true;
					}
				} else {
					createdUserId = oldUsersByEmailAndPhone.get(0).getId();
					message = messageSource.getMessage("general.current.exists",new Object[]{"User "}, currentLocale);
					status = messageSource.getMessage("code.1008",null, currentLocale);
					isError  = true;
				}
			} else {
				createdUserId = oldUsersByName.get(0).getId();
				message = messageSource.getMessage("general.current.exists",new Object[]{"User "}, currentLocale);
				status = messageSource.getMessage("code.1008",null, currentLocale);
				isError  = true;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			message = messageSource.getMessage("general.create.failure", new Object[]{"User "}, currentLocale);
			status = messageSource.getMessage("code.1004",null, currentLocale);
			isError = true;
		}
		
		Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,isError,message,createdUserId,request.getRequestURI());
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	
	/**
	 * Create Other Non-Payroll Users
	 * @param userDto
	 * @param request
	 * @return ResponseEntity
	 */
	//@PreAuthorize("hasAuthority('CREATE_OTHER_USER')")
	@PostMapping("/create-other")
	public  ResponseEntity<?> createOtherUsers(@RequestBody UserDto userDto,HttpServletRequest request,JwtAuthenticationToken auth){
		
		boolean phoneNumberConfirmed = false;
		boolean resetPassword = true;
		boolean firstLogin = false;
		boolean enabled = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		boolean accountNonExpired = true;
		boolean emailConfirmed = false;
		
		UserDetailsMinDto userDetail = null;
		AuthDetailsDto authDetails = util.getTokenAuthenticationDetails(auth);
		
		//Password
	    String plainPassword = util.generateRandomString(8, "ALPHANUMERIC");
	    String encryptedPassword = passwordEncoder.encode(plainPassword);
		
		//Password Expiry Date
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, passwordLife);
		Date passwordExpiryDate = calendar.getTime();
		
		try {	
			
			List<User> oldUserList = userService.findByUserNameOrEmailAddressOrMobileNumber(userDto.getUserName(),userDto.getEmailAddress(),userDto.getMobileNumber());
			if(oldUserList == null || oldUserList.isEmpty()) {
				
				User oldUser = null;
				if(!util.isNullOrEmpty(userDto.getNationalIdentity())) {
					oldUser = userService.findByNationalIdentityNumber(userDto.getNationalIdentity().trim());
				}
					
				if(oldUser == null) {
					
					Optional<UserCategory> userCategory = userCategoryService.findById(Constants.USER_CATEGORY_CUSTOMER);
					Set<Role> roles = new HashSet<>();
					Optional<Role> role = roleService.findById(Constants.DEFAULT_CUSTOMER_USER_ROLE);
					roles.add(role.get());
					
					User user = new User();
					
					user.setCreatedBy(authDetails.getUserId());
					user.setCreatedByUserName(authDetails.getUserName());
					user.setCreatedDate(new Date());
					user.setEmailConfirmed(emailConfirmed);
					user.setIsAccountNonExpired(accountNonExpired);
					user.setIsAccountNonLocked(accountNonLocked);
					user.setIsCredentialsNonExpired(credentialsNonExpired);
					user.setIsEnabled(enabled);
					user.setIsFirstLogin(firstLogin);
					user.setIsResetPassword(resetPassword);
					user.setPhoneNumberConfirmed(phoneNumberConfirmed);
					user.setPasswordExpiryDate(passwordExpiryDate);
					user.setRole(roles);
					
					user.setDesignation(userDto.getDesignation());
					user.setGender(userDto.getGender());
					user.setFirstName(userDto.getFirstName());
					user.setLastName(userDto.getLastName());
					user.setMiddleName(userDto.getMiddleName());
					user.setUserCategory(userCategory.get());
					user.setNationalIdentityNumber(userDto.getNationalIdentity());
					
					user.setEmailAddress(userDto.getEmailAddress());
					user.setMobileNumber(userDto.getMobileNumber());
					user.setUserName(userDto.getUserName());
					user.setPassword(encryptedPassword);
					
					User newUser = userService.saveUser(user);
					if(newUser != null) {
						
						Set<RoleDetailsMinDto> uroles = new HashSet<>();
						if (newUser.getRole() != null && !newUser.getRole().isEmpty()) {

							for (Role r : newUser.getRole()) {
								uroles.add(new RoleDetailsMinDto(r.getId(),r.getRoleName()));
							}
						}
						userDetail = new UserDetailsMinDto(String.valueOf(newUser.getId()),newUser.getFirstName(),newUser.getMiddleName(),
								newUser.getLastName(),newUser.getGender(),newUser.getUserName(),newUser.getEmailAddress()
								,newUser.getMobileNumber(),uroles);
						
						message = messageSource.getMessage("general.create.success", new Object[]{"User "}, currentLocale);
						status = messageSource.getMessage("code.1001",null, currentLocale);
						isError  = false;
						//Email Verification
						requestEmailVerification(newUser);
						
					} else {
						message = messageSource.getMessage("general.create.failure", new Object[]{"User "}, currentLocale);
						status = messageSource.getMessage("code.1002",null, currentLocale);
						isError  = true;
					}
				} else {
					message = messageSource.getMessage("general.current.exists",new Object[]{"User "}, currentLocale);
					status = messageSource.getMessage("code.1008",null, currentLocale);
					isError  = true;
				}
			} else {
				message = messageSource.getMessage("general.current.exists",new Object[]{"User "}, currentLocale);
				status = messageSource.getMessage("code.1008",null, currentLocale);
				isError  = true;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			message = messageSource.getMessage("general.create.failure", new Object[]{"User "}, currentLocale);
			status = messageSource.getMessage("code.1004",null, currentLocale);
			isError = true;
		}
		
		Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,isError,message,userDetail,request.getRequestURI());
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	
	/**
	 * Edit User Details
	 * @param UserEditDto
	 * @param request
	 * @return ResponseEntity
	 */
	//@PreAuthorize("hasAuthority('EDIT_USER')")
	@PostMapping("/edit")
	public  ResponseEntity<?> editUser(@RequestBody UserEditDto userEdit,JwtAuthenticationToken auth,HttpServletRequest request){
		
		User updateUser = null;
		try {	
			
			AuthDetailsDto authDetails = util.getTokenAuthenticationDetails(auth);
			Optional<User> oldUser = userService.findById(UUID.fromString(userEdit.getUserId()));
			
			if(oldUser.isPresent()) {
				
				User user = oldUser.get();
				user.setUpdatedBy(authDetails.getUserId());
				user.setUpdatedByUserName(authDetails.getUserName());
				user.setUpdatedDate(new Date());
				user.setIsAccountNonLocked (userEdit.isLocked());
				user.setIsEnabled(userEdit.isEnabled());
				user.setDesignation(userEdit.getDesignation());
				
				updateUser = userService.saveUser(user);
				if(updateUser != null) {
					
					message = messageSource.getMessage("general.update.success", new Object[]{"User"}, currentLocale);
					status = messageSource.getMessage("code.1001",null, currentLocale);
					isError  = false;
					
				} else {
					message = messageSource.getMessage("general.update.failure", new Object[]{"User"}, currentLocale);
					status = messageSource.getMessage("code.1002",null, currentLocale);
					isError  = true;
				}
			} else {
				message = messageSource.getMessage("general.record.not_found",new Object[]{"User"}, currentLocale);
				status = messageSource.getMessage("code.1009",null, currentLocale);
				isError  = true;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			message = messageSource.getMessage("general.update.failure", new Object[]{"User"}, currentLocale);
			status = messageSource.getMessage("code.1004",null, currentLocale);
			isError = true;
		}
		
		Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,isError,message,updateUser,request.getRequestURI());
		return ResponseEntity.status(HttpStatus.OK).body(response);
		
	}
	
	
	/**
	 * Assign user to a role 
	 * @param UserRoleDto
	 * @param request
	 * @return ResponseEntity
	 */
	//@PreAuthorize("hasAuthority('ASSIGN_USER_ROLE')")
	@PostMapping("/assign/role")
	public  ResponseEntity<?> assignUserRole(@RequestBody UserRoleDto userRoleDto,HttpServletRequest request){
		
		UserDetailsMinDto userMinDetail = null;
		
		try {	
			
			Optional<User> user = userService.findById(UUID.fromString(userRoleDto.getId()));
			if(user.isPresent()) {
				
				Set<Role> roles = new HashSet<>();
				for(Long roleId : userRoleDto.getRoleId()) {
					
					Optional<Role> role = roleService.findById(roleId);
					if(role.isPresent()) {
						
						roles.add(role.get());
					}
				}
				
				user.get().setRole(roles);
				User updatedUser = userService.saveUser(user.get());
				if(updatedUser != null) {
						
					Set<RoleDetailsMinDto> userRoles = new HashSet<>();
					if (updatedUser.getRole() != null && !updatedUser.getRole().isEmpty()) {

						for (Role role : updatedUser.getRole()) {
							userRoles.add(new RoleDetailsMinDto(role.getId(),role.getRoleName()));
						}
					}
					userMinDetail = new UserDetailsMinDto(String.valueOf(updatedUser.getId()),updatedUser.getFirstName(),updatedUser.getMiddleName()
							,updatedUser.getLastName(),updatedUser.getGender(),updatedUser.getUserName(),updatedUser.getEmailAddress()
							,updatedUser.getMobileNumber(),userRoles);
					
					message = messageSource.getMessage("general.update.success", new Object[]{"User"}, currentLocale);
					status = messageSource.getMessage("code.1001",null, currentLocale);
					isError  = false;
						
				} else {
					message = messageSource.getMessage("general.update.failure", new Object[]{"User"}, currentLocale);
					status = messageSource.getMessage("code.1004",null, currentLocale);
					isError  = true;
				}
			} else {
				message = messageSource.getMessage("general.record.not_found ", new Object[]{"User"}, currentLocale);
				status = messageSource.getMessage("code.1009",null, currentLocale);
				isError  = true;
			}
		} catch(Exception ex) {
			message = messageSource.getMessage("general.create.failure", new Object[]{"User"}, currentLocale);
			status = messageSource.getMessage("code.1004",null, currentLocale);
			isError = true;
		}
		Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,isError,message,userMinDetail,request.getRequestURI());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
	/**
	 * Request Email Verification
	 * @param user
	 */
	private void requestEmailVerification(User user) {

		new Thread(new Runnable() {
			public void run() {
				
				DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
				DateFormat dispDateTimeFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
				
				String emailVerificationToken = "";
				
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.SECOND, emailVerificationLinkLife);
				Date tokenExpiryTime = calendar.getTime();
				String time = dateTimeFormat.format(tokenExpiryTime);
				time = Base64.encodeBase64String(time.getBytes());
				
				String id = String.valueOf(user.getId());
				String data = user.getUserName()+"-"+user.getEmailAddress()+"-"+user.getLastName();
				String tokenHash = util.generateHexHMacHash(id, data);
				
				emailVerificationToken = id+"."+time+"."+tokenHash;
				
				String emailVerificationLink = emailVerificationPrefix+emailVerificationToken;
				System.out.println("emailVerificationLink**************"+emailVerificationLink);
				
				/**
				 * Create Mail
				 */ 
				MailDto mailContent = new MailDto();
				
				Map <String, Object> model = new HashMap<String,Object>();
				String fullName = user.getFirstName()+" "+(util.isNullOrEmpty(user.getMiddleName())?user.getLastName():user.getMiddleName()+" "+user.getLastName());
				model.put("verificationUri",emailVerificationLink);
				model.put("fullName",fullName);
				model.put("emailDate",dispDateTimeFormat.format(new Date()));
				model.put("linkExpiryTime",dispDateTimeFormat.format(tokenExpiryTime));
				
				mailContent.setStrMailTo(user.getEmailAddress());
				mailContent.setMailToName(fullName);
				mailContent.setMailBcc("");
				mailContent.setMailCc("");
				mailContent.setMailSubject("i-RCS Portal: User Email Verification");
				mailContent.setMailContent("");
				mailContent.setMailType(Constants.MAIL_TYPE_EMAIL_VERIFICATION);
				mailContent.setModel(model);
				
				/**
				 * Send Mail
				 */
				
				
			}
		}).start();
	}
	
	/**
	 * Request password reset link
	 * @param user
	 */
	private void requestPasswordResetEmailLink(User user) {

		new Thread(new Runnable() {
			public void run() {
				
				DateFormat dispDateTimeFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
				DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
				String passwordResetToken = "";
				
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.SECOND, emailVerificationLinkLife);
				Date tokenExpiryTime = calendar.getTime();
				String time = dateTimeFormat.format(tokenExpiryTime);
				time = Base64.encodeBase64String(time.getBytes());
				
				String id = String.valueOf(user.getId());
				String data = user.getUserName()+"-"+user.getEmailAddress()+"-"+user.getLastName();
				String tokenHash = util.generateHexHMacHash(id, data);
				
				passwordResetToken = id+"."+time+"."+tokenHash;
				
				String passwordResetLink = passwordResetLinkPrefix+passwordResetToken;
				System.out.println("passwordResetLink**************"+passwordResetLink);
				
				/**
				 * Create Mail
				 */ 
				MailDto mailContent = new MailDto();
				
				Map <String, Object> model = new HashMap<String,Object>();
				String fullName = user.getFirstName()+" "+(util.isNullOrEmpty(user.getMiddleName())?user.getLastName():user.getMiddleName()+" "+user.getLastName());
				model.put("passwordResetUri",passwordResetLink);
				model.put("fullName",fullName);
				model.put("emailDate",dispDateTimeFormat.format(new Date()));
				model.put("linkExpiryTime",dispDateTimeFormat.format(tokenExpiryTime));
				
				mailContent.setStrMailTo(user.getEmailAddress());
				mailContent.setMailToName(fullName);
				mailContent.setMailBcc("");
				mailContent.setMailCc("");
				mailContent.setMailSubject("i-RCS Portal: User password reset");
				mailContent.setMailContent("");
				mailContent.setMailType(Constants.MAIL_TYPE_PASSWORD_RESET_LINK);
				mailContent.setModel(model);
				
				/**
				 * Send Mail
				 */
			}
		}).start();
	}

	/**
	 * Request Phone Number Verification
	 * @param User
	 */
	private void requestPhoneNumberVerification(User user) {

		new Thread(new Runnable() {
			public void run() {
				
				DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
				
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.SECOND, phoneNumberVerificationTokenLife);
				Date tokenExpiryTime = calendar.getTime();
				String time = dateTimeFormat.format(tokenExpiryTime);
				
				String phoneNumberVerificationToken = util.generateRandomNumber(6);
				String id = String.valueOf(user.getId());
				
				//Save in Memory
				inMemoryUserVerificationTokens.put(id,phoneNumberVerificationToken+"."+time);
				System.out.println("phoneNumberVerificationToken*****************************"+phoneNumberVerificationToken);
				
				String smsText = phoneNumberVerificationToken+" is your i-RCS Portal verification code.";
				String phoneNumber = user.getMobileNumber();
				SmsDto sms = new SmsDto(phoneNumber,smsText);
				
				/**
				 * Send SMS
				 */
				
			}
		}).start();
	}

	/**
	 * Verify user email
	 * @param tokenDetails
	 * @param request
	 * @return ResponseEntity
	 */ 
	//@PreAuthorize("hasAuthority('VERIFY_EMAIL')")
	@GetMapping("/verify-email/{tokendetails}")
	public ResponseEntity<?> verifyUserEmail(@PathVariable("tokendetails") String tokenDetails,HttpServletRequest request){
		
		DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
		String userId = null;
		
		try {
			
			String[] tokenValues = tokenDetails.split("[.]");
			
			if(tokenValues.length == 3) {
				
				userId = tokenValues[0];
				String time = tokenValues[1];
				String tokenHash = tokenValues[2];
				
				byte[] timeDecoded = Base64.decodeBase64(time);
				String dateStr = new String(timeDecoded, StandardCharsets.UTF_8);
				Date tokenExpiryTime = dateTimeFormat.parse(dateStr);
				
				if(tokenExpiryTime.after(new Date())) {
					
					UUID userUuid = UUID.fromString(userId);
					Optional<User> user = userService.findById(userUuid);
					
					if(user.isPresent()) {
						
						if(!user.get().getEmailConfirmed()) {
							
							String data = user.get().getUserName()+"-"+user.get().getEmailAddress()+"-"+user.get().getLastName();
							String dataHash = util.generateHexHMacHash(String.valueOf(user.get().getId()), data);
							
							if(dataHash.equals(tokenHash)) {
								
								boolean emailConfirmed = true;
								user.get().setEmailConfirmed(emailConfirmed);
								
								User confirmedUser = userService.saveUser(user.get());
								if(confirmedUser != null) {
									
									message = messageSource.getMessage("message.1001",null, currentLocale);
									status = messageSource.getMessage("code.1001",null, currentLocale);
									isError = false;
									
									//Phone Number Verification
									requestPhoneNumberVerification(confirmedUser);
									
								} else {
									message = messageSource.getMessage("message.1004",null, currentLocale);
									status = messageSource.getMessage("code.1004",null, currentLocale);
									isError = true;
								}
							} else {
								message = messageSource.getMessage("message.1013",null, currentLocale);
								status = messageSource.getMessage("code.1013",null, currentLocale);
								isError = true;
							}	
						} else {
							message = messageSource.getMessage("message.1001",null, currentLocale);
							status = messageSource.getMessage("code.1001",null, currentLocale);
							isError = false;
						}			
					} else {
						message = messageSource.getMessage("message.1013",null, currentLocale);
						status = messageSource.getMessage("code.1013",null, currentLocale);
						isError = true;
					}
				} else {
					message = messageSource.getMessage("message.1015",null, currentLocale);
					status = messageSource.getMessage("code.1015",null, currentLocale);
					isError = true;
				}
			} else {
				message = messageSource.getMessage("message.1013",null, currentLocale);
				status = messageSource.getMessage("code.1013",null, currentLocale);
				isError = true;
			}
		} catch(IllegalArgumentException iex) {
			message = messageSource.getMessage("message.1013",null, currentLocale);
			status = messageSource.getMessage("code.1013",null, currentLocale);
			isError = true;
		} catch(Exception ex) {
			ex.printStackTrace();
			message = messageSource.getMessage("message.1004",null, currentLocale);
			status = messageSource.getMessage("code.1004",null, currentLocale);
			isError = true;
		}
		Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,isError,message,userId,request.getRequestURI());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
	/**
	 * Verify user reset password link
	 * @param tokenDetails
	 * @param request
	 * @return ResponseEntity
	 */ 
	//@PreAuthorize("hasAuthority('VERIFY_RESET_LINK')")
	@GetMapping("/verify/password-reset-link/{tokendetails}")
	public ResponseEntity<?> verifyUserResetPassword(@PathVariable("tokendetails") String tokenDetails,HttpServletRequest request){
		
		DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
		String userId = null;
		try {
			
			if(!util.isNullOrEmpty(tokenDetails)) {
				
				String[] tokenValues = tokenDetails.split("[.]");
				if(tokenValues.length == 3) {
					
					String id = tokenValues[0];
					String time = tokenValues[1];
					String tokenHash = tokenValues[2];
					
					byte[] timeDecoded = Base64.decodeBase64(time);
					String dateStr = new String(timeDecoded, StandardCharsets.UTF_8);
					Date tokenExpiryTime = dateTimeFormat.parse(dateStr);
					
					if(tokenExpiryTime.after(new Date())) {
						
						UUID userUuid = UUID.fromString(id);
						Optional<User> user = userService.findById(userUuid);
						
						if(user.isPresent()) {
							
							String data = user.get().getUserName()+"-"+user.get().getEmailAddress()+"-"+user.get().getLastName();
							String dataHash = util.generateHexHMacHash(String.valueOf(user.get().getId()), data);
							
							if(dataHash.equals(tokenHash)) {
								
								user.get().setEmailConfirmationTokenCount(0);
								userService.saveUser(user.get());
								
								userId = String.valueOf(user.get().getId());
								message = messageSource.getMessage("message.1001",null, currentLocale);
								status = messageSource.getMessage("code.1001",null, currentLocale);
								isError = false;
								
							} else {
								message = messageSource.getMessage("message.1017",null, currentLocale);
								status = messageSource.getMessage("code.1017",null, currentLocale);
								isError = true;
							}				
						} else {
							message = messageSource.getMessage("message.1017",null, currentLocale);
							status = messageSource.getMessage("code.1017",null, currentLocale);
							isError = true;
						}
					} else {
						message = messageSource.getMessage("message.1015",null, currentLocale);
						status = messageSource.getMessage("code.1015",null, currentLocale);
						isError = true;
					}
				} else {
					message = messageSource.getMessage("message.1017",null, currentLocale);
					status = messageSource.getMessage("code.1017",null, currentLocale);
					isError = true;
				}
			} else {
				message = messageSource.getMessage("message.1005",null, currentLocale);
				status = messageSource.getMessage("code.1005",null, currentLocale);
				isError = true;
			}
		} catch(IllegalArgumentException iex) {
			message = messageSource.getMessage("message.1017",null, currentLocale);
			status = messageSource.getMessage("code.1017",null, currentLocale);
			isError = true;
		} catch(Exception ex) {
			ex.printStackTrace();
			message = messageSource.getMessage("message.1004",null, currentLocale);
			status = messageSource.getMessage("code.1004",null, currentLocale);
			isError = true;
		}
		Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,isError,message,userId,request.getRequestURI());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
	/**
	 * Verify user phone number
	 * @param userid
	 * @param token
	 * @param request
	 * @return ResponseEntity
	 */ 
	//@PreAuthorize("hasAuthority('VERIFY_PHONE_NUMBER')")
	@GetMapping("/verify-phone-number/user/{userid}/token/{token}")
	public ResponseEntity<?> verifyUserPhoneNumber(@PathVariable("userid") String userId,@PathVariable("token") String token
			,HttpServletRequest request){
		
		DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-ddHH:mm:ss");
		
		try {
			
			if(userId != null && !userId.isBlank() && token != null && !token.isBlank()) {
				
				UUID userUuid = UUID.fromString(userId);
				Optional<User> user = userService.findById(userUuid);
				if(user.isPresent()) {
					
					String tokenDetails = inMemoryUserVerificationTokens.get(userId);
					if(tokenDetails != null && !tokenDetails.isBlank() && tokenDetails.split("[.]",0).length ==2 ) {
						
						String tokenValue = tokenDetails.split("[.]",0)[0];
						String dateValue = tokenDetails.split("[.]",0)[1];
						Date tokenExpiryTime = dateTimeFormat.parse(dateValue);
						
						if(tokenExpiryTime.after(new Date())) {
							
							if(tokenValue.equals(token)) {
								
								/**
								 * 	Finalize other users creation
								 */
								if(user.get().getUserCategory().getId().equals(Constants.USER_CATEGORY_CUSTOMER)) {
									
									//Password Expiry Date
									Calendar calendar = Calendar.getInstance();
									calendar.add(Calendar.DATE, oneTimePasswordLife);
									Date passwordExpiryDate = calendar.getTime();
									
									//One time password
								    String plainPassword = util.generateRandomString(8, "ALPHANUMERIC");
								    String encryptedPassword = passwordEncoder.encode(plainPassword);
								    user.get().setPassword(encryptedPassword);
								    user.get().setPasswordExpiryDate(passwordExpiryDate);
								    user.get().setIsResetPassword(true);
								    //Send Credential Email
								    sendFirstTimeUserCredentials(user.get(),plainPassword);
								    
								}
								
								boolean phoneNumberConfirmed = true;
								user.get().setPhoneNumberConfirmed(phoneNumberConfirmed);
								user.get().setSmsConfirmationTokenCount(0);
								
								User confirmedUser = userService.saveUser(user.get());
								if(confirmedUser != null) {
									
									message = messageSource.getMessage("message.1001",null, currentLocale);
									status = messageSource.getMessage("code.1001",null, currentLocale);
									isError = false;
									
								} else {
									message = messageSource.getMessage("message.1004",null, currentLocale);
									status = messageSource.getMessage("code.1004",null, currentLocale);
									isError = true;
								}
							} else {
								message = messageSource.getMessage("message.1014",null, currentLocale);
								status = messageSource.getMessage("code.1014",null, currentLocale);
								isError = true;
							}
						} else {
							message = messageSource.getMessage("message.1015",null, currentLocale);
							status = messageSource.getMessage("code.1015",null, currentLocale);
							isError = true;
						}
					} else {
						message = messageSource.getMessage("message.1014",null, currentLocale);
						status = messageSource.getMessage("code.1014",null, currentLocale);
						isError = true;
					}
				} else {
					message = messageSource.getMessage("message.1014",null, currentLocale);
					status = messageSource.getMessage("code.1014",null, currentLocale);
					isError = true;
				}
			} else {
				message = messageSource.getMessage("message.1014",null, currentLocale);
				status = messageSource.getMessage("code.1014",null, currentLocale);
				isError = true;
			}
		} catch(IllegalArgumentException iex) {
			message = messageSource.getMessage("message.1014",null, currentLocale);
			status = messageSource.getMessage("code.1014",null, currentLocale);
			isError = true;
		} catch(Exception ex) {
			ex.printStackTrace();
			message = messageSource.getMessage("message.1004",null, currentLocale);
			status = messageSource.getMessage("code.1004",null, currentLocale);
			isError = true;
		}
		Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,isError,message,userId,request.getRequestURI());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
	/**
	 * Send credentials notification to new user
	 * @param user
	 */
	private void sendFirstTimeUserCredentials(User user,String plainPassword) {

		new Thread(new Runnable() {
			public void run() {
				
				DateFormat dispDateTimeFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss");
				
				/**
				 * Create Mail
				 */ 
				MailDto mailContent = new MailDto();
				
				Map <String, Object> model = new HashMap<String,Object>();
				String fullName = user.getFirstName()+" "+(util.isNullOrEmpty(user.getMiddleName())?user.getLastName():user.getMiddleName()+" "+user.getLastName());
				model.put("password",plainPassword);
				model.put("fullName",fullName);
				model.put("emailDate",dispDateTimeFormat.format(new Date()));
				model.put("loginPage",loginUrl);
				
				mailContent.setStrMailTo(user.getEmailAddress());
				mailContent.setMailToName(fullName);
				mailContent.setMailBcc("");
				mailContent.setMailCc("");
				mailContent.setMailSubject("i-RCS Portal: User Credentials");
				mailContent.setMailContent("");
				mailContent.setMailType(Constants.MAIL_TYPE_USER_CREDENTIALS);
				mailContent.setModel(model);
				
				/**
				 * Send Mail
				 */
				
				
			}
		}).start();
	}
	
	/**
	 * Re-request user phone number verification token
	 * @param userId
	 */
	//@PreAuthorize("hasAuthority('RESEND_VERIFICATION_TOKEN')")
	@GetMapping("/resend/phone-number/verification/token/{userid}")
	public ResponseEntity<?> resendPhoneNumberVerificationToken(@PathVariable("userid") String userId,HttpServletRequest request) {
		
		try {
			
			if(userId != null && !userId.isBlank()) {
				UUID userUuid = UUID.fromString(userId);
				Optional<User> user = userService.findById(userUuid);
				if(user.isPresent()) {
					
					if(user.get().getSmsConfirmationTokenCount() <= Constants.MAX_SMS_CONFIRMATION_TOKEN_COUNT) {
						
						user.get().setSmsConfirmationTokenCount(user.get().getSmsConfirmationTokenCount()+1);
						
						requestPhoneNumberVerification(userService.findById(UUID.fromString(userId)).get());
						message = messageSource.getMessage("message.1001",null, currentLocale);
						status = messageSource.getMessage("code.1001",null, currentLocale);
						isError = false;
						
					} else {
						message = messageSource.getMessage("message.1021",null, currentLocale);
						status = messageSource.getMessage("code.1021",null, currentLocale);
						isError = true;
					}
				} else {
					message = messageSource.getMessage("message.1009",null, currentLocale);
					status = messageSource.getMessage("code.1009",null, currentLocale);
					isError = true;
				}
			} else {
				message = messageSource.getMessage("message.1005",null, currentLocale);
				status = messageSource.getMessage("code.1005",null, currentLocale);
				isError = true;
			}
		} catch(IllegalArgumentException iex) {
			message = messageSource.getMessage("message.1005",null, currentLocale);
			status = messageSource.getMessage("code.1005",null, currentLocale);
			isError = true;
		} catch(Exception ex) {
			ex.printStackTrace();
			message = messageSource.getMessage("message.1004",null, currentLocale);
			status = messageSource.getMessage("code.1004",null, currentLocale);
			isError = true;
		}
		
		Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,isError,message,userId,request.getRequestURI());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
	/**
	 * Change user password
	 * @param ChangePasswordDto
	 * @return ResponseEntity
	 */
	//@PreAuthorize("hasAuthority('CHANGE_PASSWORD')")
	@PostMapping("/change-password")
	public ResponseEntity<?> chengeUserPassword(@RequestBody ChangePasswordDto changePassword,HttpServletRequest request) {
		
		String userId = null;
		try {
			
			if(!util.isNullOrEmpty(changePassword.getUserId()) && !util.isNullOrEmpty(changePassword.getOldPassword())
					&& !util.isNullOrEmpty(changePassword.getNewPassword())) {
				
				UUID userUuid = UUID.fromString(changePassword.getUserId());
				Optional<User> user = userService.findById(userUuid);
				if(user.isPresent()) {
					
					byte[] oldCredDecoded = Base64.decodeBase64(changePassword.getOldPassword());
				    String plainOldPassword = new String(oldCredDecoded, StandardCharsets.UTF_8);
				    
				    if(passwordEncoder.matches(plainOldPassword, user.get().getPassword())) {
				    	
						byte[] newCredDecoded = Base64.decodeBase64(changePassword.getNewPassword());
					    String plainNewPassword = new String(newCredDecoded, StandardCharsets.UTF_8);
					    String encryptedNewPassword = passwordEncoder.encode(plainNewPassword);
					    
					    Calendar calendar = Calendar.getInstance();
						calendar.add(Calendar.DATE, passwordLife);
						Date passwordExpiryDate = calendar.getTime();
						
					    user.get().setPassword(encryptedNewPassword);
					    user.get().setPasswordExpiryDate(passwordExpiryDate);
					    user.get().setIsResetPassword(false);
					    User updatedUser = userService.saveUser(user.get());
					    
					    if(updatedUser != null) {
					    	userId = String.valueOf(updatedUser.getId());
					    	message = messageSource.getMessage("message.1001",null, currentLocale);
							status = messageSource.getMessage("code.1001",null, currentLocale);
							isError = false;
							
					    } else {
					    	message = messageSource.getMessage("message.1004",null, currentLocale);
							status = messageSource.getMessage("code.1004",null, currentLocale);
							isError = true;
					    }
				    } else {
				    	message = messageSource.getMessage("message.1016",null, currentLocale);
						status = messageSource.getMessage("code.1016",null, currentLocale);
						isError = true;
				    }
				} else {
					message = messageSource.getMessage("message.1009",null, currentLocale);
					status = messageSource.getMessage("code.1009",null, currentLocale);
					isError = true;
				}
			} else {
				message = messageSource.getMessage("message.1005",null, currentLocale);
				status = messageSource.getMessage("code.1005",null, currentLocale);
				isError = true;
			}
		} catch(IllegalArgumentException iex) {
			message = messageSource.getMessage("message.1005",null, currentLocale);
			status = messageSource.getMessage("code.1005",null, currentLocale);
			isError = true;
		} catch(Exception ex) {
			ex.printStackTrace();
			message = messageSource.getMessage("message.1004",null, currentLocale);
			status = messageSource.getMessage("code.1004",null, currentLocale);
			isError = true;
		}
		
		Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,isError,message,userId,request.getRequestURI());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	
	/**
	 * Request password reset email link
	 * @param username
	 * @param request
	 * @return ResponseEntity
	 */ 
	//@PreAuthorize("hasAuthority('REQUEST_PASSWORD_RESET_LINK')")
	@GetMapping("/request/password-reset-link/{username}")
	public ResponseEntity<?> requestPasswordResetLink(@PathVariable("username") String userName,HttpServletRequest request){
		
		try {
			
			if(userName != null && !userName.isBlank()) {
				
				Optional<User> user = userService.findByUserName(userName);
				if(user.isPresent()) {
					
					if(user.get().getEmailConfirmationTokenCount() <= Constants.MAX_EMAIL_CONFIRMATION_TOKEN_COUNT) {
						
						message = messageSource.getMessage("message.1001",null, currentLocale);
						status = messageSource.getMessage("code.1001",null, currentLocale);
						isError = false;
						
						requestPasswordResetEmailLink(user.get());
						
					} else {
						message = messageSource.getMessage("message.1021",null, currentLocale);
						status = messageSource.getMessage("code.1021",null, currentLocale);
						isError = true;
					}
				} else {
					message = messageSource.getMessage("message.1009",null, currentLocale);
					status = messageSource.getMessage("code.1009",null, currentLocale);
					isError = true;
				}
			} else {
				message = messageSource.getMessage("message.1005",null, currentLocale);
				status = messageSource.getMessage("code.1005",null, currentLocale);
				isError = true;
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			message = messageSource.getMessage("message.1004",null, currentLocale);
			status = messageSource.getMessage("code.1004",null, currentLocale);
			isError = true;
		}
		Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,isError,message,null,request.getRequestURI());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	/**
	 * Change user password
	 * @param ChangePasswordDto
	 * @return ResponseEntity
	 */
	//@PreAuthorize("hasAuthority('RESET_PASSWORD')")
	@PostMapping("/reset-password")
	public ResponseEntity<?> resetUserPassword(@RequestBody ChangePasswordDto changePassword,HttpServletRequest request) {
		
		try {
			
			if(!util.isNullOrEmpty(changePassword.getUserId()) && !util.isNullOrEmpty(changePassword.getNewPassword())) {
				
				UUID userUuid = UUID.fromString(changePassword.getUserId());
				Optional<User> user = userService.findById(userUuid);
				if(user.isPresent()) {
					
					byte[] newCredDecoded = Base64.decodeBase64(changePassword.getNewPassword());
				    String plainNewPassword = new String(newCredDecoded, StandardCharsets.UTF_8);
				    String encryptedNewPassword = passwordEncoder.encode(plainNewPassword);
				    
				    Calendar calendar = Calendar.getInstance();
					calendar.add(Calendar.DATE, passwordLife);
					Date passwordExpiryDate = calendar.getTime();
					
				    user.get().setPassword(encryptedNewPassword);
				    user.get().setPasswordExpiryDate(passwordExpiryDate);
				    user.get().setEmailConfirmationTokenCount(0);
				    User updatedUser = userService.saveUser(user.get());
				    
				    if(updatedUser != null) {
				    	message = messageSource.getMessage("message.1001",null, currentLocale);
						status = messageSource.getMessage("code.1001",null, currentLocale);
						isError = false;
						
				    } else {
				    	message = messageSource.getMessage("message.1004",null, currentLocale);
						status = messageSource.getMessage("code.1004",null, currentLocale);
						isError = true;
				    }
				} else {
					message = messageSource.getMessage("message.1009",null, currentLocale);
					status = messageSource.getMessage("code.1009",null, currentLocale);
					isError = true;
				}
			} else {
				message = messageSource.getMessage("message.1005",null, currentLocale);
				status = messageSource.getMessage("code.1005",null, currentLocale);
				isError = true;
			}
		} catch(IllegalArgumentException iex) {
			message = messageSource.getMessage("message.1005",null, currentLocale);
			status = messageSource.getMessage("code.1005",null, currentLocale);
			isError = true;
		} catch(Exception ex) {
			ex.printStackTrace();
			message = messageSource.getMessage("message.1004",null, currentLocale);
			status = messageSource.getMessage("code.1004",null, currentLocale);
			isError = true;
		}
		
		Response response = new Response(String.valueOf(Calendar.getInstance().getTime()),status,isError,message,null,request.getRequestURI());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
}
