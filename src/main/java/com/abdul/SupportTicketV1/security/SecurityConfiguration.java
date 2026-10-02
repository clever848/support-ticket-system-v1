package com.abdul.SupportTicketV1.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.abdul.SupportTicketV1.exception.CustomAccessDeniedHandler;
import com.abdul.SupportTicketV1.exception.CustomAuthenticationEntryPoint;
import com.abdul.SupportTicketV1.filter.JwtFilter;



@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {
	@Autowired
	JwtFilter jwtFilter;
	@Autowired
	CustomAuthenticationEntryPoint authEntryPoint;
	@Autowired
	CustomAccessDeniedHandler accessDeniedHandler;
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity)
	{
		return httpSecurity
			.csrf(customizer->customizer.disable())
			.authorizeHttpRequests(request->request
					.requestMatchers("/auth/**")
					.permitAll()
					.requestMatchers("/support/**")
					.hasAnyRole("SUPPORT","ADMIN")
					.requestMatchers("/admin/user/**")
					.hasAnyRole("ADMIN","SUPER_ADMIN")
					.anyRequest()
					.authenticated())
			.httpBasic(Customizer.withDefaults())
			.sessionManagement(session->session
									.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class)
			.exceptionHandling(exception->exception.authenticationEntryPoint(authEntryPoint)
										.accessDeniedHandler(accessDeniedHandler))
			.build();
	}
	@Bean
	public AuthenticationProvider authProvider(UserDetailsService userDetailsService)
	{
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
		provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
		return provider;
	}
	@Bean
	public AuthenticationManager authManger(AuthenticationConfiguration config)
	{
		return config.getAuthenticationManager();
	}
	
}
