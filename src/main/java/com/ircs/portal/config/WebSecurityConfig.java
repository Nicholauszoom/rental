package com.ircs.portal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
//import org.springframework.web.cors.CorsConfigurationSource;
import com.ircs.portal.setup.service.CustomUserDetailsService;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class WebSecurityConfig {

	@Autowired
	private CustomUserDetailsService userDetailsService;
	
	//@Autowired
    //private CorsConfigurationSource corsConfigurationSource;

	@Bean
	public static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Order(1)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http//.cors().configurationSource(corsConfigurationSource)
        //.and().
        .csrf().disable()
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers("/assets/**").permitAll()
                                .requestMatchers("/home").hasRole("User")
                				.requestMatchers("/login", "/logout").permitAll()
                				.anyRequest().fullyAuthenticated()
                				//.anyRequest().permitAll()
                ).formLogin(
                        form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/login")
                                .defaultSuccessUrl("/invoice", false)
                                .failureUrl("/login?error").permitAll()
                ).logout(
                        logout -> logout
                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                .permitAll()
                );
        return http.build();
    }
	
	
	/**
	@Bean
	@Order(2)
	public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http.cors().and().authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated());

		return http.build();
	}*/
	 
	/**
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
	}*/


	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

}
