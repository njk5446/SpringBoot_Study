package edu.pnu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.pnu.domain.Member;
import edu.pnu.persistance.MemberRepository;

@Service
public class BoardUserDetailsService implements UserDetailsService {

	@Autowired
	private MemberRepository memRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Member member = memRepo.findById(username)
				.orElseThrow(()-> new UsernameNotFoundException("Not Found"));
		System.out.println(member);
		
		// org.springframework.security.core.userdetails.User;
		// 여기에서 리턴된 User 객체와 로그인 요청 정보를 비교
		return new User(				
				member.getUsername(),
				member.getPassword(), 
				AuthorityUtils.createAuthorityList(member.getRole().toString())); // 권한은 중복 가능하므로 list로 생성
	}
	
	
}
