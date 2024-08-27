package com.rubypaper;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rubypaper.domain.Board;
import com.rubypaper.domain.Member;
import com.rubypaper.persistence.MemberRepository;

@SpringBootTest
public class RelationMappingTest {

	@Autowired
	private MemberRepository memberRepo;

	@Test
	public void testTwoWayMapping() {
		Member member = memberRepo.findById("member1").get();

		System.out.println("=".repeat(50));
		System.out.println(member.getName() + "가(이) 저장한 게시글 목록");
		System.out.println("=".repeat(50));
		List<Board> list = member.getBoardList();
		for (Board b : list)
			System.out.println(b.toString());
	}

	@Test
	public void testCascadeInsert() {
		Member m2 = Member.builder().id("member2").password("member222").name("도우너").role("Admin").build();
		for (int i = 1; i <= 100; i++) {
			Board board = new Board();
			board.setMember(m2);
			board.setTitle("title" + i);
			board.setContent("content" + i);
			board.setCreateDate(new Date());
			board.setCnt((long) (Math.random() * 100));
		}
		memberRepo.save(m2);
		System.out.println("=".repeat(50));
		System.out.println(m2.getName()+" 가(이) 작성한 게시글 삭제");
		System.out.println("=".repeat(50));
		memberRepo.deleteById(m2.getId());
	}
}
