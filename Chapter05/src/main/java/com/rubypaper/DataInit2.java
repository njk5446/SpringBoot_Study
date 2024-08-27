package com.rubypaper;

import java.util.Date;
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.rubypaper.domain.Board;
import com.rubypaper.domain.Member;
import com.rubypaper.persistence.BoardRepository;
import com.rubypaper.persistence.MemberRepository;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@Component
public class DataInit2 implements ApplicationRunner{

	private final BoardRepository boardRepo;
	private final MemberRepository memberRepo;
	
	@Override
	public void run(ApplicationArguments args) throws Exception {
		Member m1 = Member.builder().id("member1").password("member111").name("둘리").role("User")
				.build();
//		memberRepo.save(m1);
		
		Member m2 = Member.builder().id("member2").password("member222").name("도우너").role("Admin")
				.build();
//		memberRepo.save(m2);
		
		for (int i = 1; i <= 100 ; i++) {
			Board board = new Board();
			board.setMember(m1);
			board.setTitle("title"+i);
			board.setContent("content"+i);
			board.setCreateDate(new Date());
			board.setCnt((long)(Math.random()*100));
//			boardRepo.save(Board.builder()
//					.title("title"+i)
//					.content("content"+i)
//					.createDate(new Date())
//					.cnt((long)(Math.random()*100))
//					.member(m1)
//					.build());
		}
		memberRepo.save(m1);
		
//		for (int i = 1; i <= 100 ; i++) {
//			Board board = new Board();
//			board.setMember(m2);
//			board.setTitle("title"+i);
//			board.setContent("content"+i);
//			board.setCreateDate(new Date());
//			board.setCnt((long)(Math.random()*100));
////			boardRepo.save(Board.builder()
////					.title("title"+i)
////					.content("content"+i)
////					.createDate(new Date())
////					.cnt((long)(Math.random()*100))
////					.member(m2)
////					.build());
//		}
//		memberRepo.save(m2);
	}
}