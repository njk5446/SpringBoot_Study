package com.rubypaper.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rubypaper.domain.Member;
import com.rubypaper.persistence.MemberRepository;

@Service
public class MemberServicelmpl implements MemberService {

	@Autowired
	private MemberRepository memRepo;
	
	public Member getMember(Member member) {
		Optional<Member> fm = memRepo.findById(member.getId());
		if(fm.isPresent()) return fm.get();
		else return null;
	}
}
