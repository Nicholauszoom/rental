package com.dflex.ircs.portal.config;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationContext;
import org.springframework.security.oauth2.server.authorization.oidc.authentication.OidcUserInfoAuthenticationToken;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.dflex.ircs.portal.setup.entity.UserPrincipal;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class WebSecurityConfig {

	@Value("${spring.oauth2.server.uri}")
	private String oauthServer;

	JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

	@Bean
	@Order(1)
	public SecurityFilterChain authorizationServerSecurityFilterChain2(HttpSecurity http) throws Exception {

		OAuth2AuthorizationServerConfigurer authorizationServerConfigurer = new OAuth2AuthorizationServerConfigurer();
		RequestMatcher endpointsMatcher = authorizationServerConfigurer.getEndpointsMatcher();

		Function<OidcUserInfoAuthenticationContext, OidcUserInfo> userInfoMapper = (context) -> {
			OidcUserInfoAuthenticationToken authentication = context.getAuthentication();
			JwtAuthenticationToken principal = (JwtAuthenticationToken) authentication.getPrincipal();

			return new OidcUserInfo(principal.getToken().getClaims());
		};

		authorizationServerConfigurer
				.oidc((oidc) -> oidc.userInfoEndpoint((userInfo) -> userInfo.userInfoMapper(userInfoMapper)));
		http.cors().and()
				.securityMatcher("/**")
				.authorizeHttpRequests((authorize) -> authorize
						.requestMatchers("/api/authorize", "/api/employee/verify", "/api/ward/**", "/api/district/**",
								"/api/region/**","/api/token").permitAll()
						.requestMatchers("/api/user-category/**", "/api/terms-of-service/**", "/api/user/create").permitAll()
						.requestMatchers("/api/user/verify-email/**", "/api/user/verify-phone-number/**").permitAll()
						.requestMatchers("/api/user/resend/phone-number/verification/token/**").permitAll()
						.requestMatchers("/api/user/request/password-reset-link/**", "/api/user/reset-password").permitAll()
						.requestMatchers("/api/user/verify/password-reset-link/**","/api/employee/**").permitAll()
						.requestMatchers("/api/invoice/validation-v1","/api/payment/validation-v1").permitAll()
						.requestMatchers("/api/invoice/InvoiceAll","/api/invoice/InvoiceById/{id}").permitAll()
						.anyRequest().authenticated())
				.csrf(csrf -> csrf.ignoringRequestMatchers(endpointsMatcher)
						.ignoringRequestMatchers("/api/invoice/validation-v1","/api/payment/validation-v1")
						.ignoringRequestMatchers("/api/authorize", "/api/employee/**", "/api/ward/**",
								"/api/district/**", "/api/region/**","/api/token")
						.ignoringRequestMatchers("/api/user-category/**", "/api/terms-of-service/**","/api/user/create")
						.ignoringRequestMatchers("/api/user/verify-email/**", "/api/user/verify-phone-number/**")
						.ignoringRequestMatchers("/api/user/resend/phone-number/verification/token/**")
						.ignoringRequestMatchers("/api/user/request/password-reset-link/**", "/api/user/reset-password")
						.ignoringRequestMatchers("/api/user/verify/password-reset-link/**"))
				.exceptionHandling((exceptions) -> exceptions.authenticationEntryPoint(authenticationEntryPoint()))
				.apply(authorizationServerConfigurer)//;
				.and().oauth2ResourceServer().jwt().jwtAuthenticationConverter(customJwtAuthenticationConverter());

		return http.build();
	}

	@Bean
	@Order(2)
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.cors().and().authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated());

		return http.build();
	}

	@Bean
	public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
		return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
	}

	@Bean
	public RegisteredClientRepository registeredClientRepository() {

		List<RegisteredClient> registrations = new ArrayList<>();
		
		RegisteredClient portalClient = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("portal")
				.clientSecret(passwordEncoder().encode("portal321"))
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.redirectUri(oauthServer + "/oauth2/authorized")
				.scope(OidcScopes.OPENID)
				.scope("message.read")
				.scope("message.write")
				.clientSettings(
						ClientSettings.builder().requireAuthorizationConsent(false).requireProofKey(false).build())
				.tokenSettings(TokenSettings.builder().reuseRefreshTokens(true)
						.idTokenSignatureAlgorithm(SignatureAlgorithm.RS256)
						.accessTokenTimeToLive(Duration.ofSeconds(300)).refreshTokenTimeToLive(Duration.ofSeconds(1800))
						.build())
				.build();
		registrations.add(portalClient);

		RegisteredClient mobileClient = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("mobile")
				.clientSecret(passwordEncoder().encode("mobile321"))
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.redirectUri(oauthServer + "/oauth2/authorized")
				.scope(OidcScopes.OPENID)
				.scope("message.read")
				.scope("message.write")
				.clientSettings(
						ClientSettings.builder().requireAuthorizationConsent(false).requireProofKey(false).build())
				.tokenSettings(TokenSettings.builder().reuseRefreshTokens(true)
						.idTokenSignatureAlgorithm(SignatureAlgorithm.RS256)
						.accessTokenTimeToLive(Duration.ofSeconds(300)).refreshTokenTimeToLive(Duration.ofSeconds(1800))
						.build())
				.build();
		registrations.add(mobileClient);
		
		RegisteredClient portalCore = RegisteredClient.withId(UUID.randomUUID().toString())
				.clientId("portalcore")
				.clientSecret(passwordEncoder().encode("portalcore321"))
				.clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
				.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
				.authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
				.authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
				.redirectUri(oauthServer + "/oauth2/authorized")
				.scope(OidcScopes.OPENID)
				.scope("message.read")
				.scope("message.write")
				.clientSettings(
						ClientSettings.builder().requireAuthorizationConsent(false).requireProofKey(false).build())
				.tokenSettings(TokenSettings.builder().reuseRefreshTokens(true)
						.idTokenSignatureAlgorithm(SignatureAlgorithm.RS256)
						.accessTokenTimeToLive(Duration.ofSeconds(300)).refreshTokenTimeToLive(Duration.ofSeconds(1800))
						.build())
				.build();
		registrations.add(portalCore);

		return new InMemoryRegisteredClientRepository(registrations);
	}

	@Bean
	public JWKSource<SecurityContext> jwkSource() {
		KeyPair keyPair = generateRsaKey();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		RSAKey rsaKey = new RSAKey.Builder(publicKey).privateKey(privateKey).keyID(UUID.randomUUID().toString())
				.build();
		JWKSet jwkSet = new JWKSet(rsaKey);
		return new ImmutableJWKSet<>(jwkSet);
	}

	private static KeyPair generateRsaKey() {
		KeyPair keyPair;
		try {
			KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
			keyPairGenerator.initialize(2048);
			keyPair = keyPairGenerator.generateKeyPair();
		} catch (Exception ex) {
			throw new IllegalStateException(ex);
		}
		return keyPair;
	}

	@Bean
	public AuthorizationServerSettings authorizationServerSettings() {
		return AuthorizationServerSettings.builder().build();
	}


	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


	@Bean
	public CustomAuthenticationEntryPoint authenticationEntryPoint() {
		return new CustomAuthenticationEntryPoint();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer() {
		return context -> {
			if (context.getTokenType() == OAuth2TokenType.ACCESS_TOKEN) {
				Authentication principal = context.getPrincipal();
				Set<String> authorities = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
						.collect(Collectors.toSet());
				context.getClaims().claim("role", authorities);
			
				if(principal.getPrincipal() instanceof  UserPrincipal) {
					UserPrincipal customUser = (UserPrincipal)principal.getPrincipal();
					context.getClaims().claim("uuid", customUser.getId());
					context.getClaims().claim("emal", customUser.getEmail());
					context.getClaims().claim("phon", customUser.getPhoneNumber());
					context.getClaims().claim("pers", customUser.getFullName());
				}
			}
		};
	}

	@Bean
	public JwtAuthenticationConverter customJwtAuthenticationConverter() {

		JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
		converter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

		return converter;
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		final CorsConfiguration config = new CorsConfiguration();

		config.setAllowedOrigins(Arrays.asList("*"));
		config.setAllowedMethods(Arrays.asList("*"));
		// config.setAllowCredentials(true);
		config.setAllowedHeaders(Arrays.asList("*"));
		config.setExposedHeaders(Arrays.asList("*"));
		config.setMaxAge(864000L);
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return source;
	}

}
