package edu.pnu.controller;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.pnu.domain.MemberVO;

@RestController
public class MemberController {

	private List<MemberVO> memberlist = new ArrayList<>();

	public MemberController() {
		for (int i = 0; i < 10; i++) {
			MemberVO member = new MemberVO();
			member.setId(i);
			member.setName("name" + i);
			member.setPass("pass" + i);
			member.setRegidate(new Date());
			memberlist.add(member);
		}
		System.out.println("MemberController 호출");
	}

//	private List<MemberVO> memberbuildlist = new ArrayList<>();
//	public void createMemberByBuilder() {
//		for (int i = 0; i<10; i++) {
//		memberbuildlist.add(MemberVO.builder()
//				.id(i)
//				.name("name"+i)
//				.pass("pass"+i)
//				.regidate(new Date())
//				.build());
//		}
//	}

	@GetMapping("/members")
	public List<MemberVO> getMember() {
		return memberlist;
	}

	@GetMapping("/member")
	public MemberVO getMemberById(int id) {
		for (MemberVO member : memberlist) {
			if (member.getId() == id)
				return member;
		}
		return null;
	}

	@PostMapping("/member")
	public MemberVO addMemberByPost(int id, String name, String pass) {
		if (getMemberById(id) != null) {
			System.out.println("중복된 ID");
			return null;
		}
		MemberVO member = new MemberVO();
		member.setId(id);
		member.setName(name);
		member.setPass(pass);
		member.setRegidate(new Date());
		memberlist.add(member);
		return member;
	}

	@PutMapping("/member")
	public MemberVO updateMemberById(int id, String name, String pass) {
		MemberVO member = getMemberById(id);
		if (member == null)
			return null;
		member.setName(name);
		member.setPass(pass);
		member.setRegidate(new Date());
		memberlist.add(member);
		return member;
	}

	@DeleteMapping("/member")
	public int deleteMemberById(int id) {
		MemberVO member = getMemberById(id);
		if (member == null)
			return 0;
		memberlist.remove(member);
		return 1;
	}

	@PostMapping("/memberJSON")
	public MemberVO addMemberJSON(@RequestBody MemberVO memberVO) {
		if (getMemberById(memberVO.getId()) != null) {
			System.out.println("중복된 ID");
			return null;
		}
		memberVO.setRegidate(new Date());
		memberlist.add(memberVO);
		return memberVO;
	}

}

