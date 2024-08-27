package edu.pnu.config.filter;

import java.io.IOException;
import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.pnu.domain.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	// 인증 객체
	private final AuthenticationManager authenManager;
	
	// POST로 로그인 요청이 올때 인증을 시도하는 메소드
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse rsp) throws AuthenticationException {
	
		// JSON 타입을 받기 위한 매퍼
		ObjectMapper mapper = new ObjectMapper();
		try {
			// 받아온 클라이언트 정보를 기반으로 인증을 시도
			// 클라이언트 request에서 JSON 타입의 username/password를 읽어오고, member 객체에 저장
			Member member = mapper.readValue(req.getInputStream(), Member.class);
			
			// 사용자 입력값(아이디, 패스워드)을 토대로 authToken 생성
			Authentication authToken = 
					new UsernamePasswordAuthenticationToken(
							member.getUsername(), 
							member.getPassword());
			
			// AuthenticationManager의 authenticate 메소드를 이용하여 토큰을 검증 (자격 증명)
			Authentication auth = authenManager.authenticate(authToken);
			
			// 검증 완료된 객체를 반환
			return auth;
		}catch (Exception e) {
			// 자격 증명 실패시 로그 출력
			log.info(e.getMessage());
		}
		// 자격 증명 실패 응답코드 리턴
		rsp.setStatus(HttpStatus.UNAUTHORIZED.value());
		return null;
	}
	
	// 인증이 성공했을 때 실행되는 후처리 메소드
	@Override
	protected void successfulAuthentication(
			HttpServletRequest req, HttpServletResponse rsp, 
			FilterChain chain, Authentication authResult)
			throws IOException, ServletException 
	{
		// Authentication 객체에서 인증된 사용자 정보(attemptAuthentication 메서드 리턴값)를 가져와 user 객체에 저장
		// UserDetails에서 구현된 Principal 객체를 가져옴 -> SecurityUserDetailsService 인터페이스 구현 필요
		User user = (User)authResult.getPrincipal();
		
		// JWT를 이용한 토큰 설정 
		String token = JWT.create()
						.withExpiresAt(new Date(System.currentTimeMillis()+1000*60*1000)) // 토큰 만료 시간 설정
						.withClaim("username", user.getUsername()) // 페이로드에 사용자명 추가(키:"username"/값:사용자 입력 ID) 
						.sign(Algorithm.HMAC256("edu.pnu.jwt")); // 알고리즘과 키를 사용하여 토큰 서명
		// HTTP 헤더에 JWT를 추가하고 클라이언트에게 응답
		rsp.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token); 
		// HTTP 응답상태코드를 OK로 설정
		rsp.setStatus(HttpStatus.OK.value());
	}
}
