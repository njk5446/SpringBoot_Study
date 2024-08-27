package edu.pnu.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.MemberDTO;
import edu.pnu.service.MemberService;

@RestController
public class MemberController {

	MemberService ms = null;

	public MemberController() {
		System.out.println("MemberController 호출");
		ms = new MemberService();
	}

	@GetMapping("/members")
	public List<MemberDTO> getAllMem() {
		return ms.getMember();
	}

	@GetMapping("/member")
	public MemberDTO getMemById(int id) {
		return ms.getMemberById(id);
	}

	@PostMapping("/member")
	public MemberDTO addMemPost(String name, String pass) {
		return ms.addMemberByPost(name, pass);
	}

	@PutMapping("/member")
	public MemberDTO updateMemById(int id, String name, String pass) {
		return ms.updateMemberById(id, name, pass);
	}

	@DeleteMapping("/member")
	public int deleteMemById(int id) {
		return ms.deleteMemberById(id);
	}

	// @RequestBody 를 써야 Body에서 JSON 데이터를 가져올 수 있음
	@PostMapping("/memberJSON")
	public List<MemberDTO> addMemberJSON(@RequestBody MemberDTO memberDTO) {
		return ms.addMemberJSON(memberDTO);
	}
}
