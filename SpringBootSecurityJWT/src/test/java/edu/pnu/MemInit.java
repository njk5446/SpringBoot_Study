package edu.pnu;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import edu.pnu.domain.Member;
import edu.pnu.domain.Role;
import edu.pnu.persistence.MemberRepository;

@SpringBootTest
public class MemInit {

	@Autowired
	MemberRepository mr;
		
	PasswordEncoder enc = new BCryptPasswordEncoder();
	
	@Test
	public void doWork() {
		mr.save(Member.builder()
				.username("member")
				.password(enc.encode("11"))
				.role(Role.ROLE_MEMBER)
				.enabled(true)
				.build());
		mr.save(Member.builder()
				.username("manager")
				.password(enc.encode("11"))
				.role(Role.ROLE_MANAGER)
				.enabled(true)
				.build());
		mr.save(Member.builder()
				.username("admin")
				.password(enc.encode("11"))
				.role(Role.ROLE_ADMIN)
				.enabled(true)
				.build());
	}
	
}
