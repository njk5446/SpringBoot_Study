package com.rubypaper;

import java.util.Date;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.rubypaper.domain.Board;
import com.rubypaper.domain.Member;
import com.rubypaper.persistence.BoardRepository;
import com.rubypaper.persistence.MemberRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
//@Component
public class DataInit implements ApplicationRunner{

	private final BoardRepository boardRepo;
	private final MemberRepository memberRepo;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		for (int i = 1; i <= 100 ; i++) {
			boardRepo.save(Board.builder()
					.title("title"+i)
//					.writer("writer"+i)
					.content("content"+i)
					.createDate(new Date())
					.cnt((long)(Math.random()*100))
					.build());
		}
		
		for (int i = 1; i <= 100 ; i++) {
			boardRepo.save(Board.builder()
					.title("title"+i)
//					.writer("writer2"+i)
					.content("content"+i)
					.createDate(new Date())
					.cnt((long)(Math.random()*100))
					.build());
		}
		for (int i = 1; i <= 100 ; i++) {
			memberRepo.save(Member.builder()
					.id("ID"+i)
					.password("password"+(Math.random()*100))
					.name("name"+i+"ㅇㅇ")
					.role("role"+i)
					.build());
		}
	}
}