package com.dflex.ircs.portal.auth.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserPrincipal implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private User user;
	
	private String id;
	
	private String email;
	
	private String phoneNumber;
	
	private String fullName;
	
	private String workStation;
	
    public UserPrincipal(User user) {
        this.user = user;
        this.id = String.valueOf(user.getId());
        this.email = user.getEmailAddress();
        this.phoneNumber = user.getMobileNumber();
        this.fullName = user.getFirstName()+(user.getMiddleName()==null?" ":" "+user.getMiddleName()+" ")+user.getLastName();
        this.workStation = user.getWorkStation()==null?"":user.getWorkStation().getWorkStationCode();
        user.getEmailConfirmed();
        user.getPhoneNumberConfirmed();
    }
    
    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
    	
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		for (Role role : user.getRole()) {
			authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
			/*
			 * for (Permission permission : role.getPermissions()) { authorities.add(new
			 * SimpleGrantedAuthority(permission.getPermissionName())); }
			 */
		}
		return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
	public boolean isAccountNonLocked() {
    	return true;
	}

	@Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
		/*
		 * if(user.getEmailConfirmed() && user.getPhoneNumberConfirmed()) return true;
		 * else return false;
		 */
    	return true;
    }
    
    public boolean isEmailConfirmed() {
        return user.getEmailConfirmed();
    }

    public boolean isPhoneNumberConfirmed() {
        return user.getPhoneNumberConfirmed();
    }
    
    public User getUser() {
        return user;
    }
    
    public String getId() {
        return id;
    }

}
