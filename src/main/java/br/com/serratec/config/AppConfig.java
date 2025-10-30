package br.com.serratec.config;

import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.com.serratec.security.JwtAuthenticationFilter;
import br.com.serratec.security.JwtAuthorizationFilter;
import br.com.serratec.security.JwtUtil;
import br.com.serratec.service.UsuarioDetailsServiceImpl;

@EnableWebSecurity
@Configuration
public class AppConfig {
	
	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
    private UsuarioDetailsServiceImpl userDetailsServiceImpl;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http.csrf(csrf -> csrf.disable())
	        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
	        .authorizeHttpRequests(requests -> requests
	            .requestMatchers(HttpMethod.GET,"/usuarios").hasRole("ADMIN")
	            .requestMatchers(HttpMethod.POST,"/usuarios").hasRole("ADMIN")
	            .requestMatchers(HttpMethod.GET,"/pedidos").hasRole("ADMIN")
	            .requestMatchers(HttpMethod.POST,"/pedidos").hasRole("ADMIN")
	            .requestMatchers(HttpMethod.GET,"/categorias").permitAll()
	            .requestMatchers(HttpMethod.POST,"/categorias").hasRole("ADMIN")
	            .requestMatchers(HttpMethod.POST,"/produtos").hasRole("ADMIN")
	            .requestMatchers(HttpMethod.GET,"/produtos").permitAll()
	            .requestMatchers(HttpMethod.POST,"/perfis").hasRole("ADMIN")
	            .requestMatchers(HttpMethod.GET,"/clientes").hasRole("ADMIN")
	            .requestMatchers(HttpMethod.POST,"/clientes").permitAll()

	            .requestMatchers("/h2-console/**").permitAll()
	            

	        )
	        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .headers(headers -> headers.frameOptions().disable());
	    
	    
	    http.addFilterBefore(new JwtAuthenticationFilter(
	            authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), jwtUtil),
	            UsernamePasswordAuthenticationFilter.class);

	    http.addFilterBefore(new JwtAuthorizationFilter(
	            authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), jwtUtil, userDetailsService),
	            UsernamePasswordAuthenticationFilter.class);

	    return http.build();
	   	}


	
	
	@Bean
	BCryptPasswordEncoder criptografar() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:5173", "http://localhost:2000"));
		corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", corsConfiguration.applyPermitDefaultValues());
		return source;
	}
	
}