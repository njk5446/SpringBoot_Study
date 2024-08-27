package edu.pnu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import edu.pnu.domain.Role;
import edu.pnu.persistance.MemberRepository;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memRepo;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public void save(Member member) {
		member.setPassword(encoder.encode(member.getPassword()));
		member.setRole(Role.ROLE_MEMBER);
		member.setEnabled(true);
		memRepo.save(member);
	}
}
