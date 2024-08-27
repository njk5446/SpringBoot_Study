package edu.pnu.controller;

import org.springframework.http.HttpRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import edu.pnu.domain.Member;
import edu.pnu.domain.Role;
import edu.pnu.persistence.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {

	private final PasswordEncoder enc;
	private final MemberRepository mr;
	
	@GetMapping("/login")
	public void login() {
	}
	
	@GetMapping("/loginSuccess")
	public void loginSuccess() {
	}
	
	@GetMapping("/join")
	public void join() {
	}
	
	@PostMapping("/join")
	public String joinPost(HttpServletRequest req) {
		mr.save(Member.builder()
				.username(req.getParameter("username"))
				.password(enc.encode(req.getParameter("password")))
				.role(Role.ROLE_MEMBER)
				.enabled(true)
				.build());
		
		return "welcome";
	}
	
	@GetMapping("/welcome")
	public void welcome() {
	}
	
	@GetMapping("/accessDenied")
	public void accessDenied() {
	}

}
