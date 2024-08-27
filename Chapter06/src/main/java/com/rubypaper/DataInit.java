package com.rubypaper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.rubypaper.domain.Board;
import com.rubypaper.domain.Member;
import com.rubypaper.persistence.BoardRepository;
import com.rubypaper.persistence.MemberRepository;

@Component
public class DataInit implements ApplicationRunner {
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		testDataInsert();		
	}
	
	@Autowired
	private MemberRepository memRepo;
	@Autowired
	private BoardRepository boRepo;
	
	public void testDataInsert() {
		memRepo.save(Member.builder()
				.id("member1")
				.name("둘리")
				.password("pass1")
				.role("ROLE_USER")
				.build());
		
		memRepo.save(Member.builder()
				.id("member2")
				.name("도우너")
				.password("pass2")
				.role("ROLE_ADMIN")
				.build());
		
		for (int i = 1; i <= 30 ; i++) {
			boRepo.save(Board.builder()
					.writer("둘리")
					.title("둘리 제목"+i)
					.content("둘리 내용"+i)
					.build());
		}
		for (int i = 1; i <= 30 ; i++) {
			boRepo.save(Board.builder()
					.writer("도우너")
					.title("도우너 제목"+i)
					.content("도우너 내용"+i)
					.build());
		}
	}

}
