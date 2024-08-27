package edu.pnu.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.MemberVO;
import edu.pnu.service.MemberService;

@RestController
public class MemberController {
	
	MemberService ms = null;
	
	public MemberController () {
		System.out.println("MemberController 호출");
		ms = new MemberService();
	}
	
	@GetMapping("/members")
	public List<MemberVO> getMem() {
		return ms.getMember();
	}

	@GetMapping("/member")
	public MemberVO getMemById(int id) {
		return ms.getMemberById(id);
	}

	@PostMapping("/member")
	public MemberVO addMemPost(int id, String name, String pass) {
		return ms.addMemberByPost(id, name, pass);
	}

	@PutMapping("/member")
	public MemberVO updateMemById(int id, String name, String pass) {
		return ms.updateMemberById(id, name, pass);
	}

	@DeleteMapping("/member")
	public int deleteMemById(int id) {
		return ms.deleteMemberById(id);
	}

	@PostMapping("/memberJSON")
	public MemberVO addMemberJSON(@RequestBody MemberVO memberVO) {
		return ms.addMemberJSON(memberVO);
	}
}
