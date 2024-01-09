package com.dflex.ircs.portal.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

@Configuration
public class JwtGrantedAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>>{

	@Override
	@Nullable
	public Collection<GrantedAuthority> convert(Jwt jwt) {

		List<?> roles = (ArrayList) jwt.getClaims().get("role");
		if (roles == null || roles.isEmpty()) {
			return new ArrayList<>();
		}
		Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		for (Object role : roles) {
			authorities.add(new SimpleGrantedAuthority((String) role));

		}
		return authorities;
	}

}