package edu.pnu.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import edu.pnu.domain.MemberVO;

public class MemberService {
	private List<MemberVO> memberlist = new ArrayList<>();
	
	public MemberService() {
		System.out.println("MemberService 호출");
		createMember();
	}
	
	public void createMember() {
		for (int i = 0; i < 10; i++) {
			MemberVO member = new MemberVO();
			member.setId(i);
			member.setName("name" + i);
			member.setPass("pass" + i);
			member.setRegidate(new Date());
			memberlist.add(member);
		}
		System.out.println("MemberList 생성됨");
	}
	
	public List<MemberVO> getMember() {
		return memberlist;
	}
	public MemberVO getMemberById(int id) {
		for (MemberVO member : memberlist) {
			if (member.getId() == id)
				return member;
		}
		return null;
	}
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
	
	public int deleteMemberById(int id) {
		MemberVO member = getMemberById(id);
		if (member == null)
			return 0;
		memberlist.remove(member);
		return 1;
	}
	
	public MemberVO addMemberJSON(MemberVO memberVO) {
		if (getMemberById(memberVO.getId()) != null) {
			System.out.println("중복된 ID");
			return null;
		}
		memberVO.setRegidate(new Date());
		memberlist.add(memberVO);
		return memberVO;
	}
	
}
