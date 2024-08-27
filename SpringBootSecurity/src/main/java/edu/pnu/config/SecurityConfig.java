package edu.pnu.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean	
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http.authorizeHttpRequests(security -> 
			security.requestMatchers("/member/**").authenticated()
				.requestMatchers("/manager/**").hasAnyRole("MANAGER","ADMIN")
				.requestMatchers("/admin/**").hasRole("ADMIN")
				.requestMatchers("/h2-console/**").permitAll()
				.anyRequest().permitAll());
		
		http.csrf(cf -> cf.disable()); // csrf 보호 비활성화
		http.headers(hd -> hd.frameOptions(fo -> fo.disable())); // h2 console 사용을 위한 X-frameOption 비활성화
		
//		http.formLogin(form ->{});						// SpringBoot가 제공하는 로그인을 사용하겠다는 설정
		http.formLogin(form ->form.loginPage("/login")
				.defaultSuccessUrl("/loginSuccess", true));	// 사용자가 직접 만든 로그인을 사용하겠다는 설정
		
		http.exceptionHandling(ex->ex.accessDeniedPage("/accessDenied")); // 권한에 따른 접근거부 처리
		
		http.logout(logout -> 
			logout.invalidateHttpSession(true) 	// 현재 브라우저와 연결된 세션 강제종료
			.deleteCookies("JSESSIONID")		// 쿠키에 저장된 세션 삭제
			.logoutSuccessUrl("/login"));		// 로그아웃 후 이동할 URL
				
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
}
