package edu.pnu.config.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import edu.pnu.domain.Member;
import edu.pnu.persistence.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
// OncePerRequestFilter : 최초 접속시 들어온 한번의 요청에만 필터를 적용함 (포워딩시 필터 무시)
@RequiredArgsConstructor
public class JMTAuthorizationFilter extends OncePerRequestFilter {

	private final MemberRepository memRepo;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String srcToken = request.getHeader("Authorization");		// 헤더목록 중 Authorization의 값을 가져옴
		if (srcToken == null || !srcToken.startsWith("Bearer ")) {	// 토큰이 없거나 (최초 로그인) Bearer로 시작하지 않는 경우 
			filterChain.doFilter(request, response);
			return;
		}
		String jwtToken = srcToken.replace("Bearer ", ""); // 토큰 접두사 제거
		
		String username = JWT.require(Algorithm.HMAC256("edu.pnu.jwt")) 	// 토큰에서 username 추출
								.build()
								.verify(jwtToken)
								.getClaim("username").asString();
		
		Optional<Member> opt = memRepo.findById(username);
		
		if (!opt.isPresent()) {					 // 내부가 비어 있는지 확인
			filterChain.doFilter(request, response);
			return;
		}
		Member findmem = opt.get();				// 비어있지 않으면 DB에서 조회한 user 가져오기
		
		// 가져온 정보로 UserDetails 타입의 user 객체 생성
		User user = new User (findmem.getUsername(), findmem.getPassword(),
								AuthorityUtils.createAuthorityList(findmem.getRole().toString()));

		// username과 role 관리를 위한 Authentication 객체 생성
		Authentication auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
		
		// 시큐리티 세션에 등록
		SecurityContextHolder.getContext().setAuthentication(auth);
		// 필터에 추가
		filterChain.doFilter(request, response);
	}

}
