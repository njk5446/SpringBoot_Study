package edu.pnu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

import edu.pnu.config.filter.JWTAuthenticationFilter;
import edu.pnu.config.filter.JWTAuthorizationFilter;
import edu.pnu.persistence.MemberRepository;
import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final AuthenticationConfiguration ac;
	private final MemberRepository mr;
	@Bean
	PasswordEncoder passEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain httpfilter(HttpSecurity http) throws Exception {

		http.authorizeHttpRequests(ahr -> ahr
				.requestMatchers("/loginSuccess/**").authenticated()
				.requestMatchers("/member/**").authenticated()
				.requestMatchers("/manager/**").hasAnyRole("MANAGER", "ADMIN")
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.anyRequest().permitAll());

		http.formLogin(fl -> fl
				.loginPage("/login")
				.defaultSuccessUrl("/loginSuccess"));

		http.sessionManagement(sm-> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.csrf(cs -> cs.disable());
		http.headers(hd -> hd.frameOptions(fo -> fo.disable()));
		
		http.addFilter(new JWTAuthenticationFilter(ac.getAuthenticationManager()));
		http.addFilterBefore(new JWTAuthorizationFilter(mr), AuthorizationFilter.class);

		return http.build();
	}

}
