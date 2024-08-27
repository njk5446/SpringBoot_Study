package edu.pnu.service;

import java.util.List;

import edu.pnu.dao.MemberDAO;
import edu.pnu.domain.MemberDTO;

public class MemberService {

	MemberDAO dao = new MemberDAO();

	public MemberService() {
		System.out.println("memberService 호출");
	}

	public List<MemberDTO> getMember() {
		return dao.getAllMember();
	}

	public MemberDTO getMemberById(int id) {
		return dao.getMemberById(id);
	}

	public MemberDTO updateMemberById(int id, String name, String pass) {
		return dao.updateMemberById(id, name, pass);
	}

	public MemberDTO addMemberByPost(String name, String pass) {
		return dao.addMemberByPost(name, pass);
	}

	public int deleteMemberById(int id) {
		return dao.deleteMemberById(id);
	}

	public List<MemberDTO> addMemberJSON(MemberDTO memberDTO) {
		return dao.addMemberJSON(memberDTO);
	}

}
