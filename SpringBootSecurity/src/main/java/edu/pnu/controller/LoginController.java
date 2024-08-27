package edu.pnu.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.pnu.domain.Member;
import edu.pnu.service.MemberService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {
	
	private final MemberService ms;

	@GetMapping("/login")
	public void login() {
		System.out.println("로그인 요청");
	}

	@GetMapping("/loginSuccess")
	public void loginSuccess() {
		System.out.println("loginSuccess 요청");
	}

	@GetMapping("/accessDenied")
	public void accessDenied() {
		System.out.println("accessDenied");
	}

	@GetMapping("/auth")	// /auth 로 접속 시 로그인 된 유저의 User 객체를 출력
	public @ResponseBody ResponseEntity<?> auth(@AuthenticationPrincipal User user) {
		if (user == null) {
			return ResponseEntity.ok("로그인 상태가 아닙니다.");
		}
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("/join")
	public void join() {}
	
	@PostMapping("/join")
	public String joinProc(Member member) {
		ms.save(member);
		return "welcome";
	}

}
