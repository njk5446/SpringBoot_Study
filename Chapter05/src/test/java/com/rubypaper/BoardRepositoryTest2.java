package com.rubypaper;

import java.util.Date;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.rubypaper.domain.Board;
import com.rubypaper.persistence.BoardRepository;

import lombok.Builder;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class BoardRepositoryTest2 {
	
	@Autowired
	private BoardRepository boardRepo;
	
	@Test
	@Order(1)
	
	@Builder
	public void testInsertBoard() {
		Board board = Board.builder()
				.title("첫 번째 게시글")
//				.writer("테스터")
				.content("잘 등록되나요")
				.createDate(new Date())
				.cnt(0L).build();
		
		boardRepo.save(board);
	}
	
	@Test
	@Order(2)
	public void testGetBoard() {
		Board board = boardRepo.findById(1L).get();
		System.out.println(board);
	}
	
	@Test
	@Order(3)
	public void testUpdateBoard() {
		System.out.println(" == 1번 게시글 조회 ==");
		Board board = boardRepo.findById(1L).get();
		System.out.println(" == 게시글 제목 수정 ==");
		board.setTitle("제목을 수정했습니다");
		boardRepo.save(board);
	}
	
	@Test
	@Order(4)
	public void testDeleteBoard() {
		boardRepo.deleteById(1L);
	}

}